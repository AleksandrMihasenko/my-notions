import webpack from 'webpack';
import { buildPlugins } from './buildPlugins';
import { buildLoaders } from './buildLoaders';
import { buildResolve } from './buildResolve';
import { BuildOptions } from './types/config';
import { buildDevServer } from './buildDevServer';

export function buildWebpackConfig(options: BuildOptions): webpack.Configuration {
    const { mode, paths, isDev } = options;

    return {
        mode,
        entry: paths.entry,
        output: {
            filename: '[name].[contenthash].js',
            path: paths.build,
            clean: true,
        },
        module: {
            rules: buildLoaders(options),
        },
        resolve: buildResolve(options),
        devtool: isDev ? 'inline-source-map'  : undefined,
        devServer: isDev ? buildDevServer(options) : undefined,
        plugins: buildPlugins(options),
    }
}