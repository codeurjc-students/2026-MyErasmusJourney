import { ROUTES } from "@shared/constants/routes";

import ExperiencesPage from "../pages/ExperiencesPage";
import HomePage from "../pages/HomePage/HomePage";
import AboutUsPage from "../pages/AboutUsPage/AboutUsPage";
import SignUpPage from "../pages/SignUpPage/SignUpPage";
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
    path: ROUTES.SIGN_UP,
    element: <SignUpPage />
  },
  {
    path: ROUTES.EXPERIENCES,
    element: <ExperiencesPage />
  }
];