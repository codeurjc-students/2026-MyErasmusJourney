import { ROUTES } from "@shared/constants/routes";

import ExperiencesPage from "../pages/ExperiencesPage";
import HomePage from "../pages/HomePage/HomePage";
import AboutUsPage from "src/pages/AboutUsPage/AboutUsPage";
export const routes = [
  {
    path: ROUTES.HOME,
    element: <HomePage />
  },
  {
    path: ROUTES.ABOUT_US,
    element: <AboutUsPage />
  },
  {
    path: ROUTES.EXPERIENCES,
    element: <ExperiencesPage />
  }
];