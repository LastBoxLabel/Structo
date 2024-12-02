module.exports = {
    webpack(config) {
        config.resolve.alias = {
            ...config.resolve.alias,
            cytoscape: require.resolve('cytoscape'),
        };
        return config;
    },
};