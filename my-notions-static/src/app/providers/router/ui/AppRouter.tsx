import React, { Suspense } from 'react';
import { Routes, Route } from 'react-router-dom';
import { routeConfig } from 'shared/config/routeConfig/routeConfig';

function AppRouter() {
    return (
        <Suspense fallback={<div>Loading...</div>}>
            <Routes>
                {Object.values(routeConfig).map(({ element, path }) => (
                    <Route key={path} element={<div className='page-wrapper'>{element}</div>} path={path} />
                ))}
            </Routes>
        </Suspense>
    );
}

export default AppRouter;