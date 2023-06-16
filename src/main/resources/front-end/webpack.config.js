// webpack.config.js

const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const CopyWebpackPlugin = require("copy-webpack-plugin");

module.exports = {
    entry: "./src/index.js",
    watch: true,
    output: {
        filename: "main.js",
        path: path.resolve(__dirname, "..", "static"),
    },
    module: {
        // exclude node_modules
        rules: [
            {
                test: /\.css$/i,
                use: ['style-loader', 'css-loader'],
            },
            {
                test: /\.(js|jsx)$/,         // <-- added `|jsx` here
                exclude: /node_modules/,
                use: ["babel-loader"],
            },
        ],
    },
    // pass all js files through Babel
    resolve: {
        extensions: ["*", ".js", ".jsx"],    // <-- added `.jsx` here
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: path.join(__dirname, "public", "index.html"),
        }),
        new CopyWebpackPlugin({
            patterns: [
                {
                    from: path.resolve(__dirname, 'content/**/*'),
                    to: '[path]/[name][ext]'
                },
                {
                    from: path.resolve(__dirname, 'src/404.html'),
                    to: '404.html'
                },
                {
                    from: path.resolve(__dirname, 'src/403.html'),
                    to: '403.html'
                },
                {
                    from: path.resolve(__dirname, 'src/401.html'),
                    to: '401.html'
                },
                {
                    from: path.resolve(__dirname, 'src/50x.html'),
                    to: '50x.html'
                }]
        })
    ],
    // devServer: {
    //     static: {
    //         directory: path.join(__dirname, ),
    //     },
    //     port: 3000,
    //     proxy: {
    //         '/api': {
    //             target: 'http://localhost:8080',
    //         },
    //         '/oauth2/': {
    //             target: 'http://localhost:8080',
    //         },
    //         '/login/': {
    //             target: 'http://localhost:8080',
    //         }
    //     }
    // }
};