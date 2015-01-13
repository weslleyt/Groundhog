package parser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class CollectedData {
	
	//Group
	public int syncMethods;
	public int synchBlocks;	
	public int extendsThread;
	public int implementsRunnable;
	public int extendsRunnable;
	public int startCall;
	public int waitCall;
	public int notifyCall;
	public int notifyallCall;
	public int volatileMod;
	public int hashtable;
	public int hashmap;
	public int concurrentHashMap;
	public int atomicInteger;
	public int atomicLong;
	public int atomicBoolean;
	public int importJuc;
	public int arrayBlockingQueue;
	public int blockingQueue;
	public int delayQueue;
	public int linkedBlockingDeque;
	public int linkedBlockingQueue;
	public int linkedTransferQueue;
	public int priorityBlockingQueue;
	public int synchronousQueue;
	public int concurrentMap;
	public int concurrentSkipListMap;
	public int executor;
	public int callable;
	public int future;
	public int executors;
	public int executorService;
	public int scheduledExecutorService;
	public int response;
	public int runnableFuture;
	public int runnableScheduledFuture;
	public int scheduledFuture;
	public int abstractExecutorService;
	public int forkJoinPool;
	public int scheduledThreadPoolExecutor;
	public int threadPoolExecutor;
	public int forkJoinTask;
	public int futureTask;
	public int recursiveAction;
	public int recursiveTask;
	public int swingWorker;
	public int join;
	public int currentThread;
	public int interrupt;
	public int run;
	public int setDaemon;
	public int sleep;
	public int yield;
	public int getContextClassLoader;
	public int getName;
	public int cyclicBarrier;
	public int countDownLatch;
	public int lineOfCode;
	public int newThread;
	public int ReadWriteLock;
	public int Lock;
	public int Condition;
	public int abstractOwnableSynchronizer;
	public int abstractQueuedLongSynchronizer;
	public int abstractQueuedSynchronizer;
	public int lockSupport;
	public int newReentrantLock;
	public int newReentrantReadWriteLock;
	public int newReentrantReadWriteLockReadLock;
	public int newReentrantReadWriteLockWriteLock;
	public int concurrentNavigableMap;
	public int synchronizedCollection;
	public int synchronizedList;
	public int synchronizedMap;
	public int synchronizedSet;
	public int synchronizedSortedMap;
	public int synchronizedSortedSet;
	public int allSynchronizedCollections;
	
	public int synchronizedThis;
	public int synchronizedField;
	public int synchronizedOperation;
	
	public int uncaughtExceptionHandler;
	public int setDefaultUncaughtExceptionHandler;
    public int setUncaughtExceptionHandler;
    public int uncaughtException;
    
    public int semaphore;
    
    //atualizacao jss
    public int classes;
    public int methods;
    
    
    
	
	public CollectedData(){
	}
	
	public void saveLog (String folder, File file){
		File destinyFolder = new File(folder);
		
		if (!destinyFolder.exists()) {
			destinyFolder.mkdirs();
		}
		
		try{
			FileWriter log = new FileWriter(destinyFolder.getAbsolutePath() + "/" + file.getName() + ".txt");
	        BufferedWriter out = new BufferedWriter(log);
	        
	        out.write("classes :              " + classes + "\n");
	        out.write("methods :              " + this.getTotalMethods() + "\n");
	        	        
	        out.write("extends Thread :              " + extendsThread + "\n");
	        out.write("implements Runnable :         " + implementsRunnable + "\n");
	        out.write("extends Runnable :         " + extendsRunnable + "\n");
	        out.write(".start() :                    " + startCall + "\n");
	        out.write("import j.u.c :                       " + importJuc + "\n");
	        out.write("sync methods :                " + syncMethods + "\n");
	        out.write("sync blocks :                 " + synchBlocks + "\n");
	        out.write("Hashtable :                   " + hashtable + "\n");
	        out.write("HashMap :                     " + hashmap + "\n");
	        out.write("ConcurrentHashMap :           " + concurrentHashMap + "\n");
	        out.write("concurrentNavigableMap :           " + concurrentNavigableMap + "\n");
	        
	        out.write("AtomicInteger :               " + atomicInteger + "\n");
	        out.write("AtomicLong :                  " + atomicLong + "\n");
	        out.write("AtomicBoolean :               " + atomicBoolean + "\n");
	        out.write("volatile :                    " + volatileMod + "\n");
	        out.write("wait() :                      " + waitCall + "\n");
	        out.write("notify() :                    " + notifyCall + "\n");
	        out.write("notifyAll() :                 " + notifyallCall + "\n");
	        out.write("join:                          " + join + "\n");
	        out.write("currentThread:                 " + currentThread + "\n");
	        out.write("interrupt:                     " + interrupt + "\n");
	        out.write("run:                           " + run + "\n");
	        out.write("setDaemon:                     " + setDaemon + "\n");
	        out.write("sleep:                         " + sleep + "\n");
	        out.write("yield:                        " + yield + "\n");
	        out.write("getContextClassLoader:         " + getContextClassLoader + "\n");
	        out.write("arrayBlockingQueue:            " + arrayBlockingQueue + "\n");
	        out.write("blockingQueue:                 " + blockingQueue + "\n");
	        out.write("delayQueue:                    " + delayQueue + "\n");
	        
	        printMetric("linkedBlockingDeque", linkedBlockingDeque, out);
	        printMetric("linkedBlockingQueue", linkedBlockingQueue, out);
	        printMetric("linkedTransferQueue", linkedTransferQueue, out);
	        printMetric("priorityBlockingQueue", priorityBlockingQueue, out);
	        printMetric("synchronousQueue", synchronousQueue, out);
	        printMetric("concurrentMap", concurrentMap, out);
	        printMetric("concurrentSkipListMap", concurrentSkipListMap, out);
	        printMetric("concurrentNavigableMap", concurrentNavigableMap, out);
	        printMetric("executors", executors, out);
	        printMetric("executor", executor, out);
	        printMetric("callable", callable, out);
	        printMetric("future", future, out);
	        printMetric("executorService", executorService, out);
	        printMetric("scheduledExecutorService", scheduledExecutorService, out);
	        printMetric("response", response, out);
	        printMetric("runnableFuture", runnableFuture, out);
	        printMetric("runnableScheduledFuture", runnableScheduledFuture, out);
	        printMetric("scheduledFuture", scheduledFuture, out);
	        printMetric("abstractExecutorService", abstractExecutorService, out);
	        printMetric("forkJoinPool", forkJoinPool, out);
	        printMetric("scheduledThreadPoolExecutor", scheduledThreadPoolExecutor, out);
	        printMetric("threadPoolExecutor", threadPoolExecutor, out);
	        printMetric("forkJoinTask", forkJoinTask, out);
	        printMetric("futureTask", futureTask, out);
	        printMetric("recursiveAction", this.recursiveAction, out);
	        printMetric("recursiveTask", this.recursiveTask, out);
	        printMetric("swingWorker", this.swingWorker, out);
	        
	        printMetric("CyclicBarrier", this.cyclicBarrier, out);
	        printMetric("CountDownLatch", this.countDownLatch, out);
	        
            printMetric("Synchronized keyword",getSynchronizedKeywordData(),out);
            printMetric("Thread methods",getThreadMethodsData(),out);
            printMetric("Concurrent collections",getConcurrentCollectionsData(),out);
            printMetric("Executors contructs",getExecutorsData(),out);
            printMetric("Future contructs",getFutureData(),out);
            printMetric("Atomic variables",getAtomicVariablesData(),out);
            printMetric("Hash collections",getHashCollectionsData(),out);
            printMetric("Object Methods Notification",getObjectMethodsNotificationData(),out);
            printMetric("Barriers",getBarriersData(),out);
            printMetric("New Thread", this.newThread, out);  
            printMetric("Juc.Locks", this.getJucLocks(), out);
            printMetric("j.u.c", getJuc(), out);
            printMetric("j.u.c.WITHOUT.imports", getJucWithoutImport(), out);
            printMetric("synchronizedCollection", this.synchronizedCollection, out);
            printMetric("synchronizedList", this.synchronizedList, out);
            printMetric("synchronizedMap", this.synchronizedMap, out);
            printMetric("synchronizedSet", this.synchronizedSet, out);
            printMetric("synchronizedSortedMap", this.synchronizedSortedMap, out);
            printMetric("synchronizedSortedSet", this.synchronizedSortedSet, out);
            printMetric("AllSynchronizedCollections", getAllSynchronizedCollections(), out);               
            printMetric("synchronizedThis", this.synchronizedThis, out);
            printMetric("synchronizedField", this.synchronizedField, out);
            printMetric("synchronizedOperation", this.synchronizedOperation, out);
            printMetric("ReentrantLock", this.newReentrantLock, out);
            printMetric("ReentrantReadWriteLock", this.newReentrantReadWriteLock, out);
            printMetric("MethodReentrantReadWrite.Writelock", this.newReentrantReadWriteLockWriteLock, out);
            printMetric("MethodReentrantReadWrite.Readlock", this.newReentrantReadWriteLockReadLock, out);
            
            printMetric("UncaughtExceptionHandler", this.uncaughtExceptionHandler, out);
            printMetric("MethodsetDefaultUncaughtExceptionHandler", this.setDefaultUncaughtExceptionHandler, out);
            printMetric("MethodsetUncaughtExceptionHandler", this.setUncaughtExceptionHandler, out);
            printMetric("MethoduncaughtException", this.uncaughtException, out);
            
            printMetric("Condition", this.Condition, out);
            printMetric("semaphore", this.semaphore, out);
            
            
            printMetric("Lines of Code", this.lineOfCode, out);  
            
	        out.close();
	        
		}catch(IOException e){
		}		
	}	

	private void printMetric(String metricName, int number, BufferedWriter out) throws IOException
	{
		out.write(metricName + ": " + number + "\n");
	}
	
	private int getFutureData()
	{
		return this.future + this.response + this.runnableFuture + this.runnableScheduledFuture + this.scheduledFuture +
			this.forkJoinTask +  this.futureTask + this.recursiveAction + this.recursiveTask + this.swingWorker;
	}
	
	
	private int getTotalMethods(){
		//preciso diminuir com o numero de classes pois ele conta com o construtor padrao
		return this.methods - this.classes;
	}
	
	private int getExecutorsData() {
		return this.executor+this.executorService+this.scheduledExecutorService+this.abstractExecutorService+
			this.forkJoinPool+this.scheduledThreadPoolExecutor+this.threadPoolExecutor+this.executors;
	}

	private int getConcurrentCollectionsData() {
		return this.blockingQueue+this.arrayBlockingQueue+this.delayQueue+this.linkedBlockingDeque+this.linkedBlockingQueue+this.linkedTransferQueue
			+this.priorityBlockingQueue+synchronousQueue+concurrentMap+concurrentHashMap+concurrentSkipListMap;
	}

	private int getThreadMethodsData() {
		return this.startCall + this.currentThread + this.interrupt + 
			this.join + this.run + this.setDaemon + this.sleep + this.yield + this.getContextClassLoader;		
	}	
	
	private int getSynchronizedKeywordData()
	{
		return this.synchBlocks + this.syncMethods;
	}
	
	private int getAtomicVariablesData() {
		return this.atomicBoolean + this.atomicInteger + this.atomicLong;
	}
	
	private int getHashCollectionsData() {
		return this.hashmap + this.hashtable;
	}
	
	private int getObjectMethodsNotificationData() {
		return this.waitCall + this.notifyCall + this.notifyallCall;
	}
	
	private int getBarriersData() {
		return this.cyclicBarrier + this.countDownLatch;
	}
	
	private int getJucLocks(){
		return this.ReadWriteLock
		+this.Lock
		+this.Condition
		+this.abstractOwnableSynchronizer
		+this.abstractQueuedLongSynchronizer
		+this.abstractQueuedSynchronizer
		+this.lockSupport
		+this.newReentrantLock
		+this.newReentrantReadWriteLock
		+this.newReentrantReadWriteLockReadLock
		+this.newReentrantReadWriteLockWriteLock;
	}
	
	private int getAllSynchronizedCollections(){
		return this.synchronizedCollection
		+ this.synchronizedList
		+ this.synchronizedMap
		+ this.synchronizedSet
		+ this.synchronizedSortedMap
		+ this.synchronizedSortedSet;
		
	}
	
	private int getJuc(){
				
		return getJucLocks()
		+ importJuc
		+ getAtomicVariablesData()
		+ getConcurrentCollectionsData()
		+ getFutureData()
		+ getBarriersData()
		+ semaphore
		+ getExecutorsData();
	}
	
	private int getJucWithoutImport(){
		
		return getJucLocks()
		+ getAtomicVariablesData()
		+ getConcurrentCollectionsData()
		+ getFutureData()
		+ getBarriersData()
		+ semaphore
		+ getExecutorsData();
	}
	
}
