import { defineConfig } from "vitest/config";
import tsconfigPaths from "vite-tsconfig-paths";
import { playwright } from "@vitest/browser-playwright";

export default defineConfig({
  plugins: [tsconfigPaths()],
  test: {
    coverage: {
      provider: 'v8'
    },
    environment: "jsdom",
    setupFiles: ["./tests/vitest.setup.ts"],
    globals: true,
    browser: {
      enabled: true,
      provider: playwright(),
      instances: [
        { browser: "firefox" },
      ],
      headless: true,
    },
  },
});