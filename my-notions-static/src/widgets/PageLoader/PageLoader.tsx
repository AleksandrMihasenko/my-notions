import React, { FC } from 'react';
import { getClassNames } from 'shared/lib/classNames/classNames';
import * as classes from './PageLoader.module.scss';
import Loader from 'shared/ui/Loader/Loader';

interface PageLoaderProps {
    className?: string;
}

export const PageLoader: FC<PageLoaderProps> = ({ className }) => {

    return (
        <div className={getClassNames(classes.PageLoader, {}, [className])}>
            <Loader />
        </div>
    );
}

export default PageLoader;