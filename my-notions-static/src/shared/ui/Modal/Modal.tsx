import React, { FC, ReactNode, useCallback, useEffect, useRef, useState } from 'react';
import Portal from 'shared/ui/Portal/Portal';
import { getClassNames } from '../../lib/classNames/classNames';
import * as classes from './Modal.module.scss';

interface ModalProps {
    className?: string;
    children?: ReactNode;
    isOpen?: boolean;
    onClose?: () => void;
}

const ANIMATION_DELAY = 250;

export const Modal: FC<ModalProps> = (props) => {
    const { className, children, isOpen, onClose } = props;

    const [isClosing, setIsClosing] = useState(false);
    const timerRef = useRef<ReturnType<typeof setTimeout>>();

    const mods: Record<string, boolean> = {
        [classes.opened]: isOpen,
        [classes.isClosing]: isClosing
    };

    const onContentClick = (event: React.MouseEvent) => {
        event.stopPropagation();
    }

    const onCloseHandler = () => {
        if (onClose) {
            setIsClosing(true);
            timerRef.current = setTimeout(() => {
                onClose();
                setIsClosing(false);
            }, ANIMATION_DELAY)
        }
    }

    const onKeyDown = useCallback((event: KeyboardEvent) => {
        if (event.key === 'Escape') {
            onCloseHandler();
        }
    }, [onClose]);

    useEffect(() => {
        if (isOpen) {
            window.addEventListener('keydown', onKeyDown);
        }
        return () => {
            clearTimeout(timerRef.current);
            window.removeEventListener('keydown', onKeyDown);
        }
    }, [isOpen, onKeyDown]);

    return (
        <Portal>
            <div className={getClassNames(classes.Modal, mods, [className])}>
                <div className={classes.overlay} onClick={onCloseHandler}>
                    <div className={classes.content} onClick={onContentClick}>
                        {children}
                    </div>
                </div>
            </div>
        </Portal>
    );
}

export default Modal;