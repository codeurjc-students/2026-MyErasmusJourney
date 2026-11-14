import { defineConfig } from "vitest/config";
import tsconfigPaths from "vite-tsconfig-paths";
import { playwright } from "@vitest/browser-playwright";

export default defineConfig({
  plugins: [tsconfigPaths()],
  test: {
    globals: true,
    coverage: { provider: "v8" },
    projects: [
      {
        extends: true,
        test: {
          name: "unit",
          include: ["tests/unit/**/*.test.{ts,tsx}"],
          environment: "jsdom",
          setupFiles: [
            "./tests/vitest.setup.ts",
            "./tests/vitest.maplibre-mock.ts", // mock only here
          ],
        },
      },
      {
        extends: true,
        test: {
          name: "integration",
          include: ["tests/integration/**/*.test.{ts,tsx}"],
          setupFiles: ["./tests/vitest.setup.ts"], // real MapLibre
          browser: {
            enabled: true,
            headless: true,
            provider: playwright({
              launchOptions: {
                args: [
                  "--use-angle=swiftshader",
                  "--enable-unsafe-swiftshader",
                  "--ignore-gpu-blocklist",
                ],
              },
            }),
            instances: [{ browser: "firefox" }],
          },
        },
      },
    ],
  },
});