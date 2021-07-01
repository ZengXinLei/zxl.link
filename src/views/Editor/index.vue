<template>

  <div class="markdown">

    <MarkdownPro/>
  </div>
</template>

<script>

// import Markdown from '@/components/vue-markdown/components/simple/index'
// import {MarkdownPro} from  'vue-meditor'
// import MarkdownSimple from "@/components/vue-markdown/components/simple/simple";
import MarkdownPro from "@/components/vue-markdown/pro";
export default {
  name: 'Editor',
  components: {
    MarkdownPro,
    // MarkdownSimple
    // Markdown
  }
}
</script>

<style scoped>

</style>
