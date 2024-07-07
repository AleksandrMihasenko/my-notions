import React from 'react';
import { useTranslation } from 'react-i18next';

function MainPage() {
    const {t} = useTranslation();

    return (
        <div>
            {t('main-page')}
        </div>
    );
}

export default MainPage;