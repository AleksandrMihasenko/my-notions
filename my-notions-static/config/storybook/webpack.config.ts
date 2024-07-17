import webpack from 'webpack';
import path from 'path';
import { BuildPaths } from '../build/types/config';

export default ({ config }: {config: webpack.Configuration}) => {
    const paths: BuildPaths = {
        build: '',
        html: '',
        entry: '',
        src: path.resolve(__dirname, '..', '..', 'src'),
    }

    config.resolve.modules.push(paths.src);
    config.resolve.extensions.push('.ts', '.tsx');
    config.module.rules.push(
        {
            test: /\.s[ac]ss$/i,
            use: [
                'style-loader',
                {
                    loader: 'css-loader',
                    options: {
                        modules: {
                            auto: (resPath: string) => Boolean(resPath.includes('.module')),
                            localIdentName: '[path][name]_[local]'
                        }
                    }
                },
                'sass-loader',
            ],
            exclude: /node_modules/,
        },
        {
            test: /\.svg$/,
            use: ['@svgr/webpack'],
        }
    )

    return config;
}
