module.exports = {
  env: { browser: true, es2020: true },
  extends: [
    "eslint:recommended",
    "plugin:react/recommended",
    "plugin:react/jsx-runtime",
    "plugin:react-hooks/recommended",
  ],
  parserOptions: { ecmaVersion: "latest", sourceType: "module" },
  settings: { react: { version: "latest" } }, // Updated to use the latest version
  plugins: ["react", "react-hooks"], // Added 'react-hooks'
  rules: {
    "react/prop-types": "off", // You can turn off prop-types rule if you prefer
  },
};
