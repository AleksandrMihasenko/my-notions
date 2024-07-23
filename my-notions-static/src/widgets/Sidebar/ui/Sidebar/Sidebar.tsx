import React, { useState } from 'react';
import Button, {ButtonSize, ButtonTheme } from 'shared/ui/Button/Button';
import { LangSwitcher } from 'widgets/LangSwitcher';
import { ThemeSwitcher } from 'widgets/ThemeSwitcher';
import { getClassNames } from 'shared/lib/classNames/classNames';
import * as classes from './Sidebar.module.scss';

interface SidebarProps {
    className?: string;
}

export const Sidebar = ({className}: SidebarProps) => {
    const [collapsed, setCollapsed] = useState(false);

    const onToggleCollapse = () => {
        setCollapsed(!collapsed);
    }

    return (
        <div data-testid='sidebar' className={getClassNames(classes.Sidebar, {[classes.collapsed]: collapsed}, [className])}>
            <Button
                data-testid='sidebar-toggle'
                className={classes.collapseBtn}
                theme={ButtonTheme.BACKGROUND_INVERTED}
                size={ButtonSize.M}
                onClick={onToggleCollapse}
            >
                {collapsed ? '>' : '<'}
            </Button>

            <div className={classes.switchers}>
                <ThemeSwitcher />

                <LangSwitcher />
            </div>
        </div>
    );
}

export default Sidebar;