import React, { FC } from 'react';
import { getClassNames } from 'shared/lib/classNames/classNames';
import * as classes from './Loader.module.scss';

interface LoaderProps {
    className?: string;
}

export const Loader: FC<LoaderProps> = ({ className }) => {
    return (
        <div className={getClassNames(classes.Loader, {}, [className])}></div>
    );
}

export default Loader;