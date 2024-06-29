import webpack from 'webpack';
import { BuildOptions } from './types/config';

export function buildResolve({ paths }: BuildOptions): webpack.ResolveOptions {
    return {
        extensions: ['.ts', '.tsx', '.js'],
        preferAbsolute: true,
        modules: [paths.src, 'node_modules'],
        mainFiles: ['index'],
        alias: {}
    }
}
