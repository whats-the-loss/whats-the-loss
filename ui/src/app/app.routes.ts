import { Routes } from '@angular/router';
import {ProjectOverviewComponent} from "./modules/project-overview/project-overview.component";
import {RunDashboardComponent} from "./modules/dashboard/run-dashboard.component";

export const routes: Routes = [
  { path: '' , redirectTo: '/project/my-test-project', pathMatch: "full"},
  { path: 'project/:project_id' , component: ProjectOverviewComponent},
  { path: 'project/:project_id/experiment/:experiment_id' , redirectTo: 'project/:project_id/experiment/:experiment_id/dashboard', pathMatch: "full"},
  { path: 'project/:project_id/experiment/:experiment_id/dashboard', component: RunDashboardComponent}
];
