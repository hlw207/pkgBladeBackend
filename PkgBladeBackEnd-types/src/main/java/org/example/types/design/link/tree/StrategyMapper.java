package org.example.types.design.link.tree;

/**
 * @description 策略映射器
 * T 入参类型
 * D 上下文参数
 * R 返参类型
 */
public interface StrategyMapper<T, D, R> {

    /**
     * 根据入参和上下文获取待执行策略
     *
     * @param requestParameter 入参
     * @param dynamicContext   上下文
     * @return 返参
     * @throws Exception 异常
     */
    StrategyHandler<T, D, R> get(T requestParameter, D dynamicContext) throws Exception;

}
