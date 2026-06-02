import { Routes } from '@angular/router';

import { Startcheckin } from './components/startcheckin/startcheckin';
import { Checkin } from './services/checkin';
import { Confirmcheckin } from './components/confirmcheckin/confirmcheckin';

export const routes: Routes = [
    { path: 'startCheckIn', component: Startcheckin },
    { path: 'checkin', component: Checkin },
    { path: 'confirm', component: Confirmcheckin }
    
];
