package parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.tree.LabeledStatementTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.SynchronizedTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.Tree.Kind;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;


public class CodeAnalyzerTreeVisitor extends TreePathScanner<Object, Trees> {
	public CollectedData data;
    
    public CodeAnalyzerTreeVisitor(CollectedData data) {
    	this.data = data;
	}
        
    @Override
    public Object visitNewClass(NewClassTree arg0, Trees arg1) {
    	ExpressionTree type = arg0.getIdentifier();

    	
    	if (type != null){
    		
    		if (type.toString().equals("Hashtable")
    				||type.toString().contains("Hashtable<")){
    			data.hashtable++;
    		}else if (type.toString().equals("ConcurrentHashMap")||
    				type.toString().contains("ConcurrentHashMap<")){
    			data.concurrentHashMap++;
    		}else if (type.toString().equals("ConcurrentNavigableMap")||
    				type.toString().contains("ConcurrentNavigableMap<")){
    			data.concurrentNavigableMap++;
    		}else if (type.toString().equalsIgnoreCase("SynchronizedCollection") || type.toString().contains("synchronizedCollection") ){
    			data.synchronizedCollection++;
    		}else if (type.toString().contains("SynchronizedList")|| type.toString().contains("synchronizedList") ){
    			data.synchronizedList++;
    		}else if (type.toString().contains("SynchronizedMap")|| type.toString().contains("synchronizedMap") ){
    			data.synchronizedMap++;                   
			}else if (type.toString().contains("SynchronizedSet") || type.toString().contains("synchronizedSet") ){
				data.synchronizedSet++;
			}else if (type.toString().contains("SynchronizedSortedMap")|| type.toString().contains("synchronizedSortedMap") ){
				data.synchronizedSortedMap++;
			}else if (type.toString().contains("SynchronizedSortedSet")|| type.toString().contains("synchronizedSortedSet") ){
				data.synchronizedSortedSet++;
			}else if (type.toString().equals("HashMap") ||
    			type.toString().contains("HashMap<")){
    			data.hashmap++;
    		}else if (type.toString().equals("AtomicInteger")){
    			data.atomicInteger++;
    		}else if (type.toString().equals("AtomicLong")){
    			data.atomicLong++;
    		}else if (type.toString().equals("AtomicBoolean")){
    			data.atomicBoolean++;
    		}else if(type.toString().contains("ArrayBlockingQueue<")){
    			data.arrayBlockingQueue++;
    		}else if(type.toString().contains("DelayQueue<")){
    			data.delayQueue++;
    		}else if(type.toString().contains("LinkedBlockingDeque<")){
    			data.linkedBlockingDeque++;
    		}else if(type.toString().contains("LinkedBlockingQueue<")){
    			data.linkedBlockingQueue++;
    		}else if(type.toString().contains("LinkedTransferQueue<")){
    			data.linkedTransferQueue++;
    		}else if(type.toString().contains("PriorityBlockingQueue<")){
    			data.priorityBlockingQueue++;
    		}else if(type.toString().contains("SynchronousQueue<")){
    			data.synchronousQueue++;
    		}else if(type.toString().contains("ConcurrentSkipListMap<")){
    			data.concurrentSkipListMap++;
    		}else if (type.toString().equals("Executors")){ // FACTORY
    			data.executors++;
    		}else if (type.toString().equals("ForkJoinPool")){ //JAVA 7 -> remove
    			data.forkJoinPool++;
    		}else if (type.toString().equals("ScheduledThreadPoolExecutor")){
    			data.scheduledThreadPoolExecutor++;
    		}else if (type.toString().equals("ThreadPoolExecutor")){
    			data.threadPoolExecutor++;
    		}else if (type.toString().equals("ForkJoinTask")){ //JAVA 7 -> remove
    			data.forkJoinTask++;
    		}else if (type.toString().contains("FutureTask<")){ 
    			data.futureTask++;
    		}else if (type.toString().equals("RecursiveAction")){ //JAVA 7 -> remove
    			data.recursiveAction++;
    		}else if (type.toString().equals("RecursiveTask")){//JAVA 7 -> remove
    			data.recursiveTask++;
    		}else if (type.toString().equals("SwingWorker<")){
    			data.swingWorker++;
    		}else if (type.toString().equals("CyclicBarrier")){
    			data.cyclicBarrier++;
    		}else if (type.toString().equals("CountDownLatch")){
    			data.countDownLatch++;
    		}else if (type.toString().equals("Thread")){
    			data.newThread++;  
    		}else if (type.toString().equals("LockSupport")){  
    			data.lockSupport++; 
    		}else if (type.toString().equals("ReentrantLock")){
    			data.newReentrantLock++;  
    		}else if (type.toString().equals("ReentrantReadWriteLock")){
    			data.newReentrantReadWriteLock++;  
    		}		 
    	} 
    	
    	return super.visitNewClass(arg0, arg1);
    }

    @Override
    public Object visitClass(ClassTree classTree, Trees trees) {    	
    	Tree extendsClause = classTree.getExtendsClause();
    	
    	
    	    	
    	if(classTree.getKind() == Kind.CLASS && !classTree.getModifiers().toString().contains("interface")){
    		//numero de classes
        	data.classes++;
    	}
    	
    	
    	if (extendsClause != null){  	
    		if (extendsClause.toString().equals("Thread")){
    			data.extendsThread++;
    		}else if (extendsClause.toString().contains("AbstractOwnableSynchronizer")){ 
    			data.abstractOwnableSynchronizer++;  
    		}else if (extendsClause.toString().contains("AbstractQueuedLongSynchronizer")){
    			data.abstractQueuedLongSynchronizer++;  
    		}else if (extendsClause.toString().contains("AbstractQueuedSynchronizer")){
    			data.abstractQueuedSynchronizer++;  
    		}else if (extendsClause.toString().contains("AbstractExecutorService")){
    			data.abstractExecutorService++;
    		}else if (extendsClause.toString().contains("ScheduledThreadPoolExecutor")){
    			data.scheduledThreadPoolExecutor++;
    		}else if (extendsClause.toString().contains("ThreadPoolExecutor")){
    			data.threadPoolExecutor++;
    		}else if (extendsClause.toString().contains("SwingWorker<")){
    			data.swingWorker++;
    		}
    		else if (extendsClause.toString().contains("Runnable")){
    		data.extendsRunnable++;
    		}
    		
    	}
    	
    	List<? extends Tree> implementsInterfaces = classTree.getImplementsClause();
    	
    	Iterator<? extends Tree> it = implementsInterfaces.iterator();
    	
    	while (it.hasNext()){
    		Tree t = it.next();
    		String treeToString = getTreeString(t.toString());    			
    		
    		if(treeToString.equals("Runnable")){
    			data.implementsRunnable++;
    		}else if(treeToString.equals("Executor")){
    			data.executor++;
    		}else if(treeToString.equals("Callable")
    				|| treeToString.startsWith("Callable<")){
    			data.callable++;
    		}else if (treeToString.equals("Future")
    				|| treeToString.startsWith("Future<")){
    			data.future++;
    		}else if (treeToString.equals("ExecutorService")){
    			data.executorService++;
    		}else if (treeToString.equals("ScheduledExecutorService")){
    			data.scheduledExecutorService++;
    		}else if (treeToString.equals("Response")
    				|| treeToString.startsWith("Response<")){
    			data.response++;
    		}else if (treeToString.equals("RunnableFuture")
    				|| treeToString.startsWith("RunnableFuture<")){
    			data.runnableFuture++;
    		}else if (treeToString.equals("RunnableScheduledFuture")
    				|| treeToString.startsWith("RunnableScheduledFuture<")){
    			data.runnableScheduledFuture++;
    		}else if (treeToString.equals("ScheduledFuture")
    				|| treeToString.startsWith("ScheduledFuture<")){
    			data.scheduledFuture++;
    		}else if (treeToString.equals("Condition")){
    			data.Condition++;  
    		}else if (treeToString.equals("Lock")){
    			data.Lock++;  
    		}else if (treeToString.equals("ReadWriteLock")){
    			data.ReadWriteLock++;  
    		}else if(treeToString.equals("BlockingQueue")||
    				treeToString.startsWith("BlockingQueue<")){
    			data.blockingQueue++;
    		}else if(treeToString.equals("ConcurrentMap")||
    				treeToString.startsWith("ConcurrentMap<")){
    			data.concurrentMap++; 
    		}
    		else if(treeToString.equals("UncaughtExceptionHandler") || treeToString.equals("Thread.UncaughtExceptionHandler")){
			data.uncaughtExceptionHandler++;}
    	}
    	
    	return super.visitClass(classTree, trees);
    }

	private String getTreeString(String t) {
		String treeToString = t;
		int index = treeToString.lastIndexOf(".");		
		if (index!=-1)
		{
			treeToString = treeToString.substring(index+1, treeToString.length());
		}
		return treeToString;
	}
    
    @Override
    public Object visitMethodInvocation(MethodInvocationTree invoTree, Trees trees) {
    	
   	
    	invoTree.getMethodSelect();
    	
    	//nao conta certo
    	//data.methods++;
    	
   	
    	if (invoTree.getMethodSelect().toString().endsWith("start")){
    		data.startCall++;    		
    	}else if (invoTree.getMethodSelect().toString().endsWith("wait")){
    		if(invoTree.getMethodSelect().toString().length()>4 && invoTree.getMethodSelect().toString().endsWith(".wait"))
    			data.waitCall++;    			
    		else if(invoTree.getMethodSelect().toString().length()==4)
    			data.waitCall++;
    	}else if (invoTree.getMethodSelect().toString().endsWith("notifyAll")){
    		if(invoTree.getArguments().isEmpty())
    			data.notifyallCall++;
    	}else if (invoTree.getMethodSelect().toString().endsWith("notify")){
    		if(invoTree.getArguments().isEmpty())
    			data.notifyCall++;
    	}else if (invoTree.getMethodSelect().toString().endsWith("join")){
    		if(invoTree.getMethodSelect().toString().length()>4 && invoTree.getMethodSelect().toString().endsWith(".join"))
    			data.join++;    			
    		else if(invoTree.getMethodSelect().toString().length()==4)
    			data.join++;
    	}else if (invoTree.getMethodSelect().toString().endsWith("currentThread")){
    		data.currentThread++;
    	}else if (invoTree.getMethodSelect().toString().endsWith("interrupt")){
    		data.interrupt++;
    	}else if (invoTree.getMethodSelect().toString().endsWith("setDaemon")){
    		data.setDaemon++;
    	}else if (invoTree.getMethodSelect().toString().endsWith("sleep")){
    		data.sleep++;
    	}else if (invoTree.getMethodSelect().toString().endsWith("yield")){
    		data.yield++;
    	}else if (invoTree.getMethodSelect().toString().endsWith("getContextClassLoader")){
    		data.getContextClassLoader++;
    	}
    	
    	else if (invoTree.getMethodSelect().toString().endsWith("writeLock().lock")){
    		data.newReentrantReadWriteLockWriteLock++;
    	}
    	
    	else if (invoTree.getMethodSelect().toString().endsWith("readLock().lock")){
    		data.newReentrantReadWriteLockReadLock++;
    	}
    	
    	else if (invoTree.getMethodSelect().toString().endsWith("setDefaultUncaughtExceptionHandler")){
    		data.setDefaultUncaughtExceptionHandler++;
    			if (invoTree.getArguments().toString().contains("new Thread.UncaughtExceptionHandler"))
    				data.uncaughtExceptionHandler++;
    			if (invoTree.getArguments().toString().contains("void uncaughtException"))
    				data.uncaughtException++;
    			
    			
    	}   	
    	
    	
    	else if (invoTree.getMethodSelect().toString().endsWith("setUncaughtExceptionHandler")){
    		data.setUncaughtExceptionHandler++;
    	}
    	else if (invoTree.getMethodSelect().toString().endsWith("uncaughtException")){
    		data.uncaughtException++;
    	}
    
    	
    	else if (invoTree.getMethodSelect().toString().endsWith("newCachedThreadPool") ||
    			invoTree.getMethodSelect().toString().endsWith("newFixedThreadPool") ||
    			invoTree.getMethodSelect().toString().endsWith("newScheduledThreadPool") ||
    			invoTree.getMethodSelect().toString().endsWith("newSingleThreadExecutor") ||
    			invoTree.getMethodSelect().toString().endsWith("newSingleThreadScheduledExecutor")){
    		data.executors++;
    	} else if(invoTree.getMethodSelect().toString().endsWith("getBlocker")  
    			|| invoTree.getMethodSelect().toString().endsWith("park") 
    			|| invoTree.getMethodSelect().toString().endsWith("parkNanos")
    			|| invoTree.getMethodSelect().toString().endsWith("unpark")){
    		data.lockSupport++;
    	}
    	
    	
    	return super.visitMethodInvocation(invoTree, trees);
    }
	
    @Override
    public Object visitMethod(MethodTree methodTree, Trees trees) {
    	ModifiersTree modTree = methodTree.getModifiers();
    	
    	methodTree.getName();
    	
    	Set<Modifier> mods =  modTree.getFlags();
    	
    	
    	
    	Iterator<Modifier> it = mods.iterator();
    	
    	//conta com o construtor/numero de classes nao sei pq..
    	data.methods++;
    	
    	while(it.hasNext())
    	{
    		
    		Modifier modfier = it.next();
    		if (modfier.equals(Modifier.SYNCHRONIZED)){
    			//System.out.println("Nome do m�todo sync: " + methodTree.getName());
    			data.syncMethods++;
    		}
    		
    	}
    	
    	
    	
        return super.visitMethod(methodTree, trees);
    }
    
    @Override
    public Object visitSynchronized(SynchronizedTree syncTree, Trees trees) {
    	data.synchBlocks++;
    	if(syncTree.getExpression().toString().endsWith(")"))
    		data.synchronizedOperation++;
    	else if(syncTree.getExpression().toString().endsWith("this"))
    		data.synchronizedThis++;
    	else
    		data.synchronizedField++;
    	
    	return super.visitSynchronized(syncTree, trees);
    }
    
    @Override
    public Object visitModifiers(ModifiersTree modifiersTree, Trees trees) {
    	Set<Modifier> mods =  modifiersTree.getFlags();
    	
    	Iterator<Modifier> it = mods.iterator();
    	
    	while(it.hasNext())
    	{
    		Modifier modfier = it.next();
    		if (modfier.equals(Modifier.VOLATILE)){
    			data.volatileMod++;
    		}
    	}    	
    	
    	
    	return super.visitModifiers(modifiersTree, trees);
    }
}

