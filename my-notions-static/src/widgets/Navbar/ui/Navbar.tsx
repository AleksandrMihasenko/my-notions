import React, { useCallback, useState } from 'react';
import AppLink, { AppLinkTheme } from 'shared/ui/AppLink/AppLink';
import Modal from 'shared/ui/Modal/Modal';
import Button, { ButtonTheme } from 'shared/ui/Button/Button';
import { getClassNames } from 'shared/lib/classNames/classNames';
import * as classes from './Navbar.module.scss';

interface NavbarProps {
    className?: string;
}

export const Navbar = ({className}: NavbarProps) => {
    const [isAuthModalOpen, setIsAuthModalOpen] = useState(false);

    const onToggleModal = useCallback(() => {
        setIsAuthModalOpen((prevState) => !prevState);
    }, [])

    return (
        <div className={getClassNames(classes.Navbar, {}, [className])}>
            <div>
                <Button
                    theme={ButtonTheme.OUTLINE}
                    className={classes.loginBtn}
                    onClick={onToggleModal}
                >
                    Login
                </Button>

                <Modal isOpen={isAuthModalOpen} onClose={onToggleModal}>
                    Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                </Modal>
            </div>

            <div className={classes.links}>
                <AppLink to={'/'} theme={AppLinkTheme.SECONDARY}>Main</AppLink>
                <AppLink to={'/about'} theme={AppLinkTheme.SECONDARY}>About</AppLink>
            </div>
        </div>
    );
}

export default Navbar;