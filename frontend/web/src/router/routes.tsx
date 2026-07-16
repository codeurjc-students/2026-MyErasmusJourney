import { ROUTES } from "@shared/constants/routes";

import ExperiencesPage from "../pages/ExperiencesPage";
import HomePage from "../pages/HomePage/HomePage";
export const routes = [
  {
    path: ROUTES.HOME,
    element: <HomePage />
  },
  {
    path: ROUTES.EXPERIENCES,
    element: <ExperiencesPage />
  }
];