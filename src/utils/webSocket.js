import defaultSettings from '@/settings'
import store from '@/store/index'
function RequestWebSocket(type_id="",onMsg=()=>{}){

  this.lock=false
  this.socket=null;
  this.type_id=type_id
  this.msg=""
  this.init=()=>{
    this.socket=new WebSocket("ws://"+defaultSettings.baseURL+"/zxl/"+this.type_id)
    this.socket.onopen=()=>{

      console.log("连接成功 ")
    }

    this.socket.onclose=()=>{
      console.log("断开了")
      this.reconnect()
    }

    this.socket.onerror=()=>{
      this.reconnect()
    }
    this.socket.onmessage=(e)=>{
      let type=this.type_id.split("_")[0]
      switch (type){
        case "reciveMsg":
          let comment=JSON.parse(e.data)
          store.commit("ADD_COMMENT",comment)

      }

      this.msg=e
    }
  }

  this.reconnect=()=>{
      // lock加锁，防止onclose、onerror两次重连

      if(!this.lock){
        this.lock=true


        // 进行重连
        setTimeout(()=>{
          console.log("重连")
          this.init();
          this.lock=false
        },10);
      }


  }
}

export default new RequestWebSocket("")

