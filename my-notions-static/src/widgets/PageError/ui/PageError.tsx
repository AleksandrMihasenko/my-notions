import React from 'react';
import Button from 'shared/ui/Button/Button';
import { useTranslation } from 'react-i18next';
import { getClassNames } from 'shared/lib/classNames/classNames';
import * as classes from './PageError.module.scss';

interface PageErrorProps {
    className?: string;
}

export const PageError = ({ className }: PageErrorProps) => {
    const {t} = useTranslation();

    const reloadPage = () => {
        location.reload();
    }

    return (
        <div className={getClassNames(classes.PageError, {}, [className])}>
            <p>{t('page-error')}</p>

            <Button className={getClassNames(classes.ReloadButton)} onClick={reloadPage}>
                {t('reload-page')}
            </Button>
        </div>
    );
}

export default PageError;