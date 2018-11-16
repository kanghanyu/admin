package com.khy.config;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.SafeEncoder;

/***
 * redis对象需要不断的从jedispool 中获取
 * 然后关闭才能重复使用
 * 如果jedispool 设置的maxTotal 中的数量是100
 * 然后通过 Jedis jedis = jedispool.jedisPool.getResource()
 * 获取了100次之后再次获取则报错
 * 
 * @author kanghanyu
 *
 */
@Configuration
@PropertySource(value = "classpath:redis.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "redis")
public class RedisUtils {
	
	private JedisPool jedisPool;
	/** 操作Key的方法 */
	public Keys KEYS = new Keys();
	/** 对存储结构为String类型的操作 */
	public Strings STRINGS = new Strings();
	/** 对存储结构为List类型的操作 */
	public Lists LISTS = new Lists();
	/** 对存储结构为HashMap类型的操作 */
	public Hash HASH = new Hash();
	public Khy Khy = new Khy();
	
	@PostConstruct
	private void initTipMap(){
		 if(null == jedisPool){
			 synchronized(this){
				 if(null == jedisPool){
					JedisPoolConfig pool = new JedisPoolConfig();
					pool.setMaxIdle(maxIdle);
					pool.setMinIdle(minIdle);
					pool.setMaxTotal(maxTotal);
					jedisPool = new JedisPool(pool, host, port);
				 }
			 }
		 }
	}
	
	/***
	 * 获取redis对象
	 * @Description
	 * @author khy
	 * @date  2018年7月25日下午2:50:39
	 * @return
	 */
	public Jedis getJedis() {
		Jedis jedis = null;
		if (null != jedisPool) {
			jedis = jedisPool.getResource();
			jedis.auth(password);
		}
		return jedis;
	}
	
	
	/**
	 * 释放redis
	 * @Description
	 * @author khy
	 * @date  2018年7月25日上午11:02:13
	 * @param jedis
	 */
	private void closeJedis(Jedis jedis) {
		if(null != jedis){
			jedis.close();
		}
	}

	
	/**
	 * 加锁的方法内容
	 * @Description
	 * @author khy
	 * @date  2018年7月25日上午11:40:13
	 * @param locaName 加锁的名称
	 * @param acquireTimeout 获取锁的超时时间，超过这个时间则放弃获取锁
	 * @param seconds 超时时间，上锁后超过此时间则自动释放锁
	 * @return
	 */
	public String lockWithTimeout(String locaName, long acquireTimeout, int seconds) {
		Jedis jedis = getJedis();
		String retIdentifier = null;
		try {
			// 随机生成一个value
			String identifier = UUID.randomUUID().toString();
			// 锁名，即key值
			String lockKey = "lock:" + locaName;

			// 获取锁的超时时间，超过这个时间则放弃获取锁
			long end = System.currentTimeMillis() + acquireTimeout;
			while (System.currentTimeMillis() < end) {
				if (jedis.setnx(lockKey, identifier) == 1) {//表示加锁成功
					jedis.expire(lockKey, seconds);
					// 返回value值，用于释放锁时间确认
					retIdentifier = identifier;
					return retIdentifier;
				}
				// 返回-1代表key没有设置超时时间，为key设置一个超时时间
				if (jedis.ttl(lockKey) == -1) {
					jedis.expire(lockKey, seconds);
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		} catch (JedisException e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return retIdentifier;
	}
	
	
	/***
	 * 释放redis锁的内容
	 * @Description
	 * @author khy
	 * @date  2018年7月25日上午11:47:39
	 * @param lockName
	 * @param identifier
	 * @return
	 */
	public boolean releaseLock(String lockName, String identifier) {
		Jedis jedis = getJedis();
		String lockKey = "lock:" + lockName;
		boolean retFlag = false;
		try {
			if(StringUtils.isNotBlank(identifier)){
				while (true) {
					// 监视lock，准备开始事务
					jedis.watch(lockKey);
					// 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
					if (identifier.equals(jedis.get(lockKey))) {
						Transaction transaction = jedis.multi();
						transaction.del(lockKey);
						List<Object> results = transaction.exec();
						if (results == null) {
							continue;
						}
						retFlag = true;
					}
					jedis.unwatch();
					break;
				}
			}
		} catch (JedisException e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return retFlag;
	}
	
	
public class Keys {
		
		public Set<String> keys(String pattern) {
			Jedis jedis = getJedis();
			Set<String> set = jedis.keys(pattern);
			closeJedis(jedis);
			return set;
		}
		
		/**
		 * 设置key的过期时间，以秒为单位
		 * 
		 * @param String
		 *            key
		 * @param 时间,已秒为单位
		 * @return 影响的记录数
		 */
		public long expire(String key, int seconds) {
			Jedis jedis = getJedis();
			Long ret = jedis.expire(key, seconds);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 删除key对应的记录
		 * 
		 * @param String
		 *            key
		 * @return 删除的记录数
		 */
		public long del(String key) {
			Jedis jedis = getJedis();
			Long ret = jedis.del(key);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 删除key对应的记录
		 * 
		 * @param String
		 *            key
		 * @return 删除的记录数
		 */
		public long del(byte key[]) {
			Jedis jedis = getJedis();
			Long ret = jedis.del(key);
			closeJedis(jedis);
			return ret;
		}
	}

	public class Hash {

		/**
		 * 从hash中删除指定的存储
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            fieid 存储的名字
		 * @return 状态码，1成功，0失败
		 */
		public long hdel(String key, String fieid) {
			Jedis jedis = getJedis();
			Long ret = jedis.hdel(key, fieid);
			closeJedis(jedis);
			return ret;
		}


		/**
		 * 返回hash中指定存储位置的值
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            fieid 存储的名字
		 * @return 存储对应的值
		 */
		public String hget(String key, String fieid) {
			Jedis jedis = getJedis();
			String ret = jedis.hget(key, fieid);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 以Map的形式返回hash中的存储和值
		 * 
		 * @param String
		 *            key
		 * @return Map<Strinig,String>
		 */
		public Map<String, String> hgetall(String key) {
			Jedis jedis = getJedis();
			Map<String, String> map = jedis.hgetAll(key);
			closeJedis(jedis);
			return map;
		}

		/**
		 * 添加对应关系，如果对应关系已存在，则覆盖
		 * 
		 * @param Strin
		 *            key
		 * @param Map<String,String>
		 *            对应关系
		 * @return 状态，成功返回OK
		 */
		public String hmset(String key, Map<String, String> map) {
			Jedis jedis = getJedis();
			String ret = jedis.hmset(key, map);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 添加一个对应关系
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            fieid
		 * @param String
		 *            value
		 * @return 状态码 1成功，0失败，fieid已存在将更新，也返回0
		 **/
		public long hset(String key, String fieid, String value) {
			Jedis jedis = getJedis();
			Long ret = jedis.hset(key, fieid, value);
			closeJedis(jedis);
			return ret;
		}
	}

	public class Strings {
		/**
		 * 根据key获取记录
		 * 
		 * @param String
		 *            key
		 * @return 值
		 */
		public String get(String key) {
			Jedis jedis = getJedis();
			String ret = jedis.get(key);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 添加有过期时间的记录
		 * 
		 * @param String
		 *            key
		 * @param int
		 *            seconds 过期时间，以秒为单位
		 * @param String
		 *            value
		 * @return String 操作状态
		 */
		public String setEx(String key, int seconds, String value) {
			Jedis jedis = getJedis();
			String ret = jedis.setex(key, seconds, value);
			closeJedis(jedis);
			return ret;
		}


		/**
		 * 添加一条记录，仅当给定的key不存在时才插入
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            value
		 * @return long 状态码，1插入成功且key不存在，0未插入，key存在
		 */
		public long setnx(String key, String value) {
			Jedis jedis = getJedis();
			Long ret = jedis.setnx(key, value);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 添加记录,如果记录已存在将覆盖原有的value
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            value
		 * @return 状态码
		 */
		public String set(String key, String value) {
			return set(SafeEncoder.encode(key), SafeEncoder.encode(value));
		}

		/**
		 * 添加记录,如果记录已存在将覆盖原有的value
		 * 
		 * @param byte[]
		 *            key
		 * @param byte[]
		 *            value
		 * @return 状态码
		 */
		public String set(byte[] key, byte[] value) {
			Jedis jedis = getJedis();
			String ret = jedis.set(key, value);
			closeJedis(jedis);
			return ret;
		}


		/**
		 * 将key对应的value减去指定的值，只有value可以转为数字时该方法才可用
		 * 
		 * @param String
		 *            key
		 * @param long
		 *            number 要减去的值
		 * @return long 减指定值后的值
		 */
		public long decrBy(String key, long number) {
			Jedis jedis = getJedis();
			Long ret = jedis.decrBy(key, number);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * <b>可以作为获取唯一id的方法</b><br/>
		 * 将key对应的value加上指定的值，只有value可以转为数字时该方法才可用
		 * 
		 * @param String
		 *            key
		 * @param long
		 *            number 要增加的值
		 * @return long 相加后的值
		 */
		public long incrBy(String key, long number) {
			Jedis jedis = getJedis();
			Long ret = jedis.incrBy(key, number);
			closeJedis(jedis);
			return ret;
		}
		
		public long incr(String key) {
			Jedis jedis = getJedis();
			Long ret = jedis.incr(key);
			closeJedis(jedis);
			return ret;
		}
	}

	public class Lists {
		/**
		 * List长度
		 * 
		 * @param String
		 *            key
		 * @return 长度
		 */
		public long llen(String key) {
			long ret = llen(SafeEncoder.encode(key));
			return ret;
		}

		/**
		 * List长度
		 * 
		 * @param byte[]
		 *            key
		 * @return 长度
		 */
		public long llen(byte[] key) {
			Jedis jedis = getJedis();
			Long ret = jedis.llen(key);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 覆盖操作,将覆盖List中指定位置的值
		 * 
		 * @param byte[]
		 *            key
		 * @param int
		 *            index 位置
		 * @param byte[]
		 *            value 值
		 * @return 状态码
		 */
		public String lset(byte[] key, int index, byte[] value) {
			Jedis jedis = getJedis();
			String ret = jedis.lset(key, index, value);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 覆盖操作,将覆盖List中指定位置的值
		 * 
		 * @param key
		 * @param int
		 *            index 位置
		 * @param String
		 *            value 值
		 * @return 状态码
		 */
		public String lset(String key, int index, String value) {
			return lset(SafeEncoder.encode(key), index, SafeEncoder.encode(value));
		}

		/**
		 * 在value的相对位置插入记录
		 * 
		 * @param key
		 * @param LIST_POSITION
		 *            前面插入或后面插入
		 * @param String
		 *            pivot 相对位置的内容
		 * @param String
		 *            value 插入的内容
		 * @return 记录总数
		 */
		public long linsert(String key, LIST_POSITION where, String pivot, String value) {
			Jedis jedis = getJedis();
			long ret = linsert(SafeEncoder.encode(key), where, SafeEncoder.encode(pivot), SafeEncoder.encode(value));
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 在指定位置插入记录
		 * 
		 * @param String
		 *            key
		 * @param LIST_POSITION
		 *            前面插入或后面插入
		 * @param byte[]
		 *            pivot 相对位置的内容
		 * @param byte[]
		 *            value 插入的内容
		 * @return 记录总数
		 */
		public long linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value) {
			Jedis jedis = getJedis();
			Long ret = jedis.linsert(key, where, pivot, value);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 获取List中指定位置的值
		 * 
		 * @param String
		 *            key
		 * @param int
		 *            index 位置
		 * @return 值
		 **/
		public String lindex(String key, int index) {
			return SafeEncoder.encode(lindex(SafeEncoder.encode(key), index));
		}

		/**
		 * 获取List中指定位置的值
		 * 
		 * @param byte[]
		 *            key
		 * @param int
		 *            index 位置
		 * @return 值
		 **/
		public byte[] lindex(byte[] key, int index) {
			Jedis jedis = getJedis();
			byte[] ret = jedis.lindex(key, index);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 将List中的第一条记录移出List
		 * 
		 * @param String
		 *            key
		 * @return 移出的记录
		 */
		public String lpop(String key) {
			return SafeEncoder.encode(lpop(SafeEncoder.encode(key)));
		}

		/**
		 * 将List中的第一条记录移出List
		 * 
		 * @param byte[]
		 *            key
		 * @return 移出的记录
		 */
		public byte[] lpop(byte[] key) {
			Jedis jedis = getJedis();
			byte[] ret = jedis.lpop(key);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 将List中最后第一条记录移出List
		 * 
		 * @param String
		 *            key
		 * @return 移出的记录
		 */
		public String rpop(String key) {
			Jedis jedis = getJedis();
			String ret = jedis.rpop(key);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 将List中最后第一条记录移出List
		 * 
		 * @param byte[]
		 *            key
		 * @return 移出的记录
		 */
		public byte[] rpop(byte[] key) {
			Jedis jedis = getJedis();
			byte[] ret = jedis.rpop(key);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 向List尾部追加记录
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            value
		 * @return 记录总数
		 */
		public long lpush(String key, String value) {
			return lpush(SafeEncoder.encode(key), SafeEncoder.encode(value));
		}

		/**
		 * 向List头部追加记录
		 * 
		 * @param String
		 *            key
		 * @param String
		 *            value
		 * @return 记录总数
		 */
		public long rpush(byte[] key, byte[] value) {
			Jedis jedis = getJedis();
			Long ret = jedis.rpush(key, value);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 向List头部追加记录
		 * 
		 * @param byte[]
		 *            key
		 * @param byte[]
		 *            value
		 * @return 记录总数
		 */
		public long rpush(String key, String value) {
			Jedis jedis = getJedis();
			Long ret = jedis.rpush(key, value);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 向List中追加记录
		 * 
		 * @param byte[]
		 *            key
		 * @param byte[]
		 *            value
		 * @return 记录总数
		 */
		public long lpush(byte[] key, byte[] value) {
			Jedis jedis = getJedis();
			Long ret = jedis.lpush(key, value);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 获取指定范围的记录，可以做为分页使用
		 * 
		 * @param String
		 *            key
		 * @param long
		 *            start
		 * @param long
		 *            end
		 * @return List
		 */
		public List<String> lrange(String key, long start, long end) {
			Jedis jedis = getJedis();
			List<String> ret = jedis.lrange(key, start, end);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 获取指定范围的记录，可以做为分页使用
		 * 
		 * @param byte[]
		 *            key
		 * @param int
		 *            start
		 * @param int
		 *            end 如果为负数，则尾部开始计算
		 * @return List
		 */
		public List<byte[]> lrange(byte[] key, int start, int end) {
			Jedis jedis = getJedis();
			List<byte[]> ret = jedis.lrange(key, start, end);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 删除List中c条记录，被删除的记录值为value
		 * 
		 * @param byte[]
		 *            key
		 * @param int
		 *            c 要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
		 * @param byte[]
		 *            value 要匹配的值
		 * @return 删除后的List中的记录数
		 */
		public long lrem(byte[] key, int c, byte[] value) {
			Jedis jedis = getJedis();
			Long ret = jedis.lrem(key, c, value);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 删除List中c条记录，被删除的记录值为value
		 * 
		 * @param String
		 *            key
		 * @param int
		 *            c 要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
		 * @param String
		 *            value 要匹配的值
		 * @return 删除后的List中的记录数
		 */
		public long lrem(String key, int c, String value) {
			return lrem(SafeEncoder.encode(key), c, SafeEncoder.encode(value));
		}

		/**
		 * 算是删除吧，只保留start与end之间的记录
		 * 
		 * @param byte[]
		 *            key
		 * @param int
		 *            start 记录的开始位置(0表示第一条记录)
		 * @param int
		 *            end 记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
		 * @return 执行状态码
		 */
		public String ltrim(byte[] key, int start, int end) {
			Jedis jedis = getJedis();
			String ret = jedis.ltrim(key, start, end);
			closeJedis(jedis);
			return ret;
		}

		/**
		 * 算是删除吧，只保留start与end之间的记录
		 * 
		 * @param String
		 *            key
		 * @param int
		 *            start 记录的开始位置(0表示第一条记录)
		 * @param int
		 *            end 记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
		 * @return 执行状态码
		 */
		public String ltrim(String key, int start, int end) {
			return ltrim(SafeEncoder.encode(key), start, end);
		}
	}
	
	private String password;// redis链接验证密码
	private String host;
	private int port;
	private int maxIdle;
	private int minIdle;
	private int maxTotal;

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	public int getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}
	public int getMaxTotal() {
		return maxTotal;
	}
	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}
	
	public class Khy{
		public String getValue(String key){
			Jedis jedis = getJedis();
			String ret = jedis.get(key);
			jedis.close();
			return ret;
		}
		
		public String get(String key) {
			Jedis jedis = getJedis();
			String ret = jedis.get(key);
			closeJedis(jedis);
			return ret;
		}
		
		public String setValue(String key,String value){
			Jedis jedis = getJedis();
			String ret = jedis.set(key, value);
			jedis.close();
			return ret;
		}
	}
}

