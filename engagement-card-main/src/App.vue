<template>
    <div id="app">
      <router-view></router-view>
      <vfooter></vfooter>
    </div>
</template>

<script>
  import vfooter from './components/footer.vue'
  export default {
    name: 'app',
    data () {
      return {
        footerShow: this.$store.state.footerShow
      }
    },
    computed: {
    	changrFooterShow () {
          return this.$store.state.footerShow
    	}
    },
    watch: {
      changrFooterShow: function (val, oldVal) {
        this.footerShow = val
      }
    },
    components: {
      vfooter
    },
    methods: {
      toLogin: function () {
        this.$router.push('/login')
      }
    }
  }
</script>
<style>
	.card222 {
	  width: 40% !important;
	}
	
	.card333 {
	  flex-shrink: 0;
	}
</style>
<style scoped>
  @import 'assets/css/base.css';
</style>

