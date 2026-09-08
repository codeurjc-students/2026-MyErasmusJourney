import { ROUTES } from "@shared/constants/routes";

import ExperiencesPage from "../pages/ExperiencesPage/ExperiencesPage";
import HomePage from "../pages/HomePage/HomePage";
import AboutUsPage from "../pages/AboutUsPage/AboutUsPage";
import SignUpPage from "../pages/SignUpPage/SignUpPage";
import LogInPage from "../pages/LogInPage/LogInPage";
import UserPage from "../pages/UserPage/UserPage";
import AvailableSoonPage from "../pages/AvailableSoonPage/AvailableSoonPage";
import CityFormPage from "../pages/CityFormPage/CityFormPage";
import ExperienceFormPage from "../pages/ExperienceFormPage/ExperienceFormPage";
import DetailedExperiencePage from "../pages/DetailedExperiencePage/DetailedExperiencePage";
import ErrorPage from "../pages/ErrorPage/ErrorPage";

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
    path: ROUTES.LOG_IN,
    element: <LogInPage />
  },
  {
    path: ROUTES.EXPERIENCES,
    element: <ExperiencesPage />
  },
  {
    path: ROUTES.USER,
    element: <UserPage />
  },
  {
    path: ROUTES.AVAILABLE_SOON,
    element: <AvailableSoonPage />
  },
  {
    path: ROUTES.CITY_FORM,
    element: <CityFormPage />
  },
  {
    path: ROUTES.EXPERIENCES_FORM,
    element: <ExperienceFormPage />
  },
  {
    path: ROUTES.DETAILED_EXPERIENCE,
    element: <DetailedExperiencePage />
  },
  {
    path: ROUTES.ERROR_PAGE,
    element: <ErrorPage />
  }
];