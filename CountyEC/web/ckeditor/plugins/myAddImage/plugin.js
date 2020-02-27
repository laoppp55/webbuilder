CKEDITOR.plugins.add( 'myAddImage',{
    init : function( editor )
    {   
       /*
       /* ��ȡCKEditorFuncNum
       */
       var getFuncNum = function(url) {
          var reParam = new RegExp('(?:[\?&]|&amp;)CKEditorFuncNum=([^&]+)', 'i') ;
          var match = url.match(reParam) ;
          return (match && match.length > 1) ? match[1] : '' ;
        };
       /*
       /*  iframe onload���� 
        *  ��ο��Է������棬���ݲ�ͬ�ķ���ֵ���н��д���
        */
        var getAjaxResult = function (t){
            var _id = this.getId();
            var _doc = this.getFrameDocument();
            //��ȡҳ�淵��ֵ
            var data = _doc.getBody().getHtml();
            //firebrowser�Ĵ���
            CKEDITOR.tools.callFunction(t.listenerData, data);
            this.removeListener('load', getAjaxResult);
        }

        CKEDITOR.dialog.add( 'myAddImage', function( editor )
        {
            return {
                    title : '���ͼƬ',
                    minWidth : 400,
                    minHeight : 200,
                    contents : 
                    [
                        {
                            id : 'addImage',
                            label : '���ͼƬ',
                            title : '���ͼƬ',
                            filebrowser : 'uploadButton',
                            elements :
                            [
                              {    
                                  id : 'txtUrl',
                                  type : 'text',
                                  label : 'ͼƬ��ַ',
                                  required: true
                              },
                              {
                                    id : 'photo',
                                    type : 'file',
                                    label : '�ϴ�ͼƬ',
                                    style: 'height:40px',
                                    size : 38
                              },
                              {
                                   type : 'fileButton',
                                   id : 'uploadButton',
                                   label : '�ϴ�',
                                   filebrowser :
                                   {
                                        action : 'QuickUpload',
                                        target : 'addImage:txtUrl',
                                        onSelect:function(fileUrl, errorMessage){
                                            //�����������������Ĳ���
                                        }
                                   },
                                   onClick: function(){
                                        var d = this.getDialog();
                                        var _photo =  d.getContentElement('addImage','photo');
                                        _funcNum = getFuncNum(_photo.action);
                                        var _iframe =  CKEDITOR.document.getById(_photo._.frameId);
                                        //���Բ鿴ckeditor.event doc �˽�˶δ���
                                        //http://docs.cksource.com/ckeditor_api/
                                        _iframe.on('load', getAjaxResult, _iframe, _funcNum);
                                   },
                                   'for' : [ 'addImage', 'photo']
                              }
                            ]
                        }
                   ],
                   onOk : function(){
                       _src = this.getContentElement('addImage', 'txtUrl').getValue();
                       if (_src.match(/(^\s*(\d+)((px)|\%)?\s*$)|^$/i)) {
                           alert('��������ַ�����ϴ��ļ�');
                           return false;
                       }
                       this.imageElement = editor.document.createElement( 'img' );
                       this.imageElement.setAttribute( 'alt', '' );
                       this.imageElement.setAttribute( 'src', _src );
                       //ͼƬ����editor�༭��
                       editor.insertElement( this.imageElement );
                   }
            };
        });
        editor.addCommand( 'myImageCmd', new CKEDITOR.dialogCommand( 'myAddImage' ) );
        editor.ui.addButton( 'AddImage',
        {
                label : 'ͼƬ',
                icon:'images/images.jpg', //toolbar��icon�ĵ�ַ,Ҫ�Լ��ϴ�ͼƬ��images��
                command : 'myImageCmd'
        });
    },
    requires : [ 'dialog' ]
});