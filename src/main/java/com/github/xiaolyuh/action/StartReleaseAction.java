package com.github.xiaolyuh.action;

import com.github.xiaolyuh.GitFlowPlus;
import com.github.xiaolyuh.utils.ConfigUtil;
import com.github.xiaolyuh.valve.merge.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 开始发布
 *
 * @author yuhao.wang3
 */
public class StartReleaseAction extends AbstractMergeAction {

    public StartReleaseAction() {
        super("开始发布", "将当前开发分支合并到发布分支，加锁，防止再有开发分支合并到发布分支",
                IconLoader.getIcon("/icons/start.svg"));
    }

    @Override
    protected String getTargetBranch(Project project) {
        return ConfigUtil.getConfig(project).get().getReleaseBranch();
    }

    @Override
    protected String getDialogTitle(Project project) {
        return "发布";
    }

    @Override
    protected String getTaskTitle(Project project) {
        return String.format("将 %s 分支，合并到 %s 分支", GitFlowPlus.getInstance().getCurrentBranch(project), getTargetBranch(project));
    }

    @Override
    protected List<Valve> getValves() {
        List<Valve> valves = new ArrayList<>();
        valves.add(ChangeFileValve.getInstance());
        valves.add(LockValve.getInstance());
        valves.add(MergeValve.getInstance());
        valves.add(ReleaseLockNotifyValve.getInstance());
        return valves;
    }
}
