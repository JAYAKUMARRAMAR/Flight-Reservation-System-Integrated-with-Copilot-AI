import { Routes } from '@angular/router';

import { StartCheckinComponent } from './components/startcheckin/startcheckin';
import { Checkin } from './components/checkin/checkin';
import { Confirmcheckin } from './components/confirmcheckin/confirmcheckin';

export const routes: Routes = [
    { path: 'startCheckIn', component: StartCheckinComponent },
    { path: 'checkin', component: Checkin },
    { path: 'confirm', component: Confirmcheckin }
    
];
