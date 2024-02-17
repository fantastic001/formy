new Vue({
    el: '#app',
    data: {
        dynamicComponent: null,
    },
    methods: {
        async loadComponent() {
            try {
                const url = '/fetch-component?url=http://example.com/my-dynamic-component.js';
                const response = await axios.get(url);
                const componentCode = response.data;
                // Dynamically execute the component code and register it
                const ComponentClass = Function('return ' + componentCode)();
                this.dynamicComponent = Vue.component('dynamic-component', ComponentClass);
            } catch (error) {
                console.error('Failed to load dynamic component:', error);
            }
        }
    },
    mounted() {
        this.loadComponent();
    },
});
