import Home from './src/views/Home.vue';
import Login from './src/views/Login.vue';
import Registration from './src/views/Registration.vue';
import Profile from './src/views/Profile.vue';

import LogOut from './src/views/Logout.vue';


import UpdateProfile from './src/views/UpdateProfile.vue';


import System from './src/views/System.vue';



const routes = [
    { path: '/', component: Home },
    { path: '/login', component: Login},
    { path: '/register', component: Registration},
    { path: '/profile', component: Profile},
    { path: '/updateUser', component: UpdateProfile},
    { path: '/logout', component: LogOut},
   {path: '/sys', component: System},   
];

export default routes;
