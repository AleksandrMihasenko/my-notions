import React from 'react';
import { useTranslation } from 'react-i18next';
import { getClassNames } from 'shared/lib/classNames/classNames';
import * as classes from './NotFoundPage.module.scss';

function NotFoundPage() {
    const {t} = useTranslation();

    return (
        <div className={getClassNames(classes.NotFoundPage)}>
            {t('not-found-page')}
        </div>
    );
}

export default NotFoundPage;