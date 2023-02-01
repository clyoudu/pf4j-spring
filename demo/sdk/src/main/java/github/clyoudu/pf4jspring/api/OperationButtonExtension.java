package github.clyoudu.pf4jspring.api;

import org.pf4j.ExtensionPoint;

/**
 * @author leichen
 */
public interface OperationButtonExtension extends ExtensionPoint {

    /**
     * 按钮名称
     *
     * @return 按钮名称
     */
    String name();

    /**
     * 响应点击事件
     *
     * @return 结果
     */
    String onClick();


}
