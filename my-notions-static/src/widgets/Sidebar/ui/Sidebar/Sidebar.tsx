import React, { useState } from 'react';
import { getClassNames } from 'shared/lib/classNames/classNames';
import { ThemeSwitcher } from 'widgets/ThemeSwitcher';
import { LangSwitcher } from 'widgets/LangSwitcher';
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
        <div className={getClassNames(classes.Sidebar, {[classes.collapsed]: collapsed}, [className])}>
            <button onClick={onToggleCollapse}>toggle</button>

            <div className={classes.switchers}>
                <ThemeSwitcher />

                <LangSwitcher />
            </div>
        </div>
    );
}

export default Sidebar;