import {Routes} from '@angular/router';
import {ProjectOverviewComponent} from "./modules/project-overview/project-overview.component";
import {RunDashboardComponent} from "./modules/dashboard/run-dashboard.component";
import {RunsListPageComponent} from "./modules/runs-list-page/runs-list-page.component";

export const routes: Routes = [
  {
    path: '', redirectTo: '/project/my-test-project', pathMatch: "full"
  },
  {
    path: 'project/:project_id', component: ProjectOverviewComponent,
    data: {
      breadcrumbPath: ['Project', ':project_id']
    },
  },
  {
    path: 'project/:project_id/experiment/:experiment_id',
    redirectTo: 'project/:project_id/experiment/:experiment_id/dashboard',
    pathMatch: "full"
  },
  {
    path: 'project/:project_id/experiment/:experiment_id/dashboard', component: RunDashboardComponent,
    data: {
      breadcrumbPath: ['Project', ':project_id', 'Experiment', ':experiment_id', 'Dashboard']
    },
  },
  {
    path: 'project/:project_id/experiment/:experiment_id/runs', component: RunsListPageComponent,
    data: {
      breadcrumbPath: ['Project', ':project_id', 'Experiment', ':experiment_id', 'Runs']
    },
  }
];
