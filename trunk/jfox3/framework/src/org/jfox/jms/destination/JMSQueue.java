/* JFox, the OpenSource J2EE Application Server
 *
 * Distributable under GNU LGPL license by gun.org
 * more details please visit http://www.huihoo.org/jfox
 */

package org.jfox.jms.destination;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueReceiver;

/**
 * JMS Queue
 * 维护 JMS Message Queue，并且负责分发消息
 *
 * @author <a href="mailto:young_yy@hotmail.com">Young Yang</a>
 */
public class JMSQueue extends JMSDestination implements Queue, Runnable{

    private static ExecutorService threadExecutor = Executors.newCachedThreadPool();

    private static Comparator<Message> MESSAGE_COMPARATOR = new Comparator<Message>(){

        public int compare(Message msg1, Message msg2) {
            try {
                return Integer.valueOf(msg1.getJMSPriority()).compareTo(Integer.valueOf(msg2.getJMSPriority()));
            }
            catch(JMSException e) {
                return 0;
            }
        }
    };

    private transient PriorityBlockingQueue<Message> queue = new PriorityBlockingQueue<Message>(0, MESSAGE_COMPARATOR);

    private List<QueueReceiver> receivers = new ArrayList<QueueReceiver>(2);

    public JMSQueue(String name) {
		super(name);
	}

	public String getQueueName() throws JMSException {
		return getName();
	}

	public boolean isTopic() {
		return false;
	}

    public synchronized void putMessage(Message msg){
        queue.offer(msg);
        // 交给线程池执行消息分发工作
        threadExecutor.submit(this);
    }

    public synchronized Message popMessage() {
        try {
            return queue.take();
        }
        catch(InterruptedException e) {
            //TODO: logger
            e.printStackTrace();
            return null;
        }
    }

    public void stop(){
        threadExecutor.shutdown();
    }

    public void run() {
        // 分发消息
        Message message = popMessage();
        if(message != null) {
            System.out.println("Hello,World!");
        }
    }
    
    public static void main(String[] args) {

	}
}
