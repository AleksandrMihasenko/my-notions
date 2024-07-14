import React, { FC } from 'react';
import Loader from 'shared/ui/Loader/Loader';
import { getClassNames } from 'shared/lib/classNames/classNames';
import * as classes from './PageLoader.module.scss';

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