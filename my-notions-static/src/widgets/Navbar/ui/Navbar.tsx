import React from 'react';
import AppLink, { AppLinkTheme } from 'shared/ui/AppLink/AppLink';
import { getClassNames } from 'shared/lib/classNames/classNames';
import * as classes from './Navbar.module.scss';

interface NavbarProps {
    className?: string;
}

export const Navbar = ({className}: NavbarProps) => {
    return (
        <div className={getClassNames(classes.Navbar, {}, [className])}>
            <div className={classes.links}>
                <AppLink to={'/'} theme={AppLinkTheme.SECONDARY}>Main</AppLink>
                <AppLink to={'/about'} theme={AppLinkTheme.SECONDARY}>About</AppLink>
            </div>
        </div>
    );
}

export default Navbar;