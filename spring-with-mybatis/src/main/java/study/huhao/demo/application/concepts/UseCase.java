package study.huhao.demo.application.concepts;

/**
 * UseCase（用例）用于承载应用场景和应用需求，以及其他Domain层无法承载的逻辑，例如：
 *
 * <ul>
 *     <li>应用需求的输入验证（业务需要的输入验证应放在Domain层中）</li>
 *     <li>依赖注入框架</li>
 *     <li>事务</li>
 *     <li>跨上下文或跨聚合调用</li>
 *     <li>其他应用层面的依赖</li>
 * </ul>
 */
public interface UseCase {
}
