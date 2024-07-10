import React from 'react';
import Button, { ButtonTheme } from 'shared/ui/Button/Button';
import { useTranslation } from 'react-i18next';
import { getClassNames } from 'shared/lib/classNames/classNames';
import * as classes from './LangSwitcher.module.scss';

interface LangSwitcherProps {
    className?: string;
}

export const LangSwitcher = ({className}: LangSwitcherProps) => {
    const {t, i18n} = useTranslation();

    const toggleTranslation = () => {
        i18n.changeLanguage(i18n.language === 'en' ? 'fr' : 'en');
    }

    return (
        <div className={getClassNames(classes.LangSwitcher, {}, [className])}>
            <Button theme={ButtonTheme.CLEAR} onClick={toggleTranslation}>{t('language')}</Button>
        </div>
    );
}

export default LangSwitcher;