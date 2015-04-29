package uk.co.quidos.common.component.threadpool;

import uk.co.quidos.common.component.threadpool.ThreadPool.ThreadDiagnose;

/**
 * 诊断信息接口
 * @author liqiang
 */
public interface ThreadPoolDiagnose {

	public boolean running();

	public int maxsize();

	public int size();

	public ThreadDiagnose[] getDiagnose();

}
