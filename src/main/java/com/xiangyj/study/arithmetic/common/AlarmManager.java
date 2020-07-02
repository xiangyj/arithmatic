package common;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 告警器
 */
public enum AlarmManager implements Runnable {
    // 告警器的唯一实例
    INSTANCE;
    private final AtomicBoolean init = new AtomicBoolean(false);
    AlarmManager() {}
    public void init() {
        // 使用AtomicBoolean的CAS操作确保工作者线程只会被创建（并启动）一次
        if (init.compareAndSet(false, true)) {
            // 创建并启动工作者线程
            new Thread(this).start();
        }
    }

    @Override
    public void run() {
        // 上报逻辑
    }
}
