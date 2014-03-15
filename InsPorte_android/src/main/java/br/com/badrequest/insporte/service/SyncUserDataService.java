package br.com.badrequest.insporte.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EService;
import com.googlecode.androidannotations.annotations.UiThread;

@EService
public class SyncUserDataService extends Service {

    public static final String INTENT_STATUS_SYNC = "intentStatusSync";

    public static final String DATA_REMAINING_EXTRA = "DATA_REMAINING";

    public static final String IMAGE_REMAINING_EXTRA = "IMAGE_REMAINING";

    public static final String FAILURE_EXTRA = "FAILURE";

    @Override
    public void onCreate() {
        System.out.println("DEBUG");
        syncData();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Background
    void syncData() {
        System.out.println("DEBUG");

//        int falhas = 0;
//        try {
//            RespostaDataSource respostaDS = new RespostaDataSource(getApplicationContext());
//            List<Resposta> respostasQuestionario = respostaDS.listarTodosQuestionarios();
//            String baseURL = Util.getUrlWS(getApplicationContext());
//            int questionariosRestantes = respostasQuestionario.size();
//
//            //Envia questionarios
//            for (Resposta respostaQuestionario : respostasQuestionario) {
//                Log.d(Constantes.TAG, "SyncRespostasService - Enviando QUESTIONARIO " + respostaQuestionario.getId());
//                boolean sucesso
//                        = enviarJsonQuestionario(converterFormatoWS(respostaQuestionario.getDados()), baseURL + Constantes.METODO_GRAVA_FORMULARIO, (new Gson()).fromJson(respostaQuestionario.getDados(), Questionario.class));
//
//                if (sucesso) {
//                    Log.d(Constantes.TAG, "SyncRespostasService - JSON " + respostaQuestionario.getId() + " enviado com sucesso!");
//                    respostaDS.marcarQuestionarioEnviado(respostaQuestionario.getId());
//                } else {
//                    Log.d(Constantes.TAG, "SyncRespostasService - JSON " + respostaQuestionario.getId() + " erro no envio.");
//                    falhas++;
//                }
//                sendStatus(questionariosRestantes--, QUESTIONARIO_RESTANTES_EXTRA);
//            }
//
//            //Envia imagens
//            String fotosPath = getExternalFilesDir(Constantes.PATH_FOTOS).getAbsolutePath();
//            int imagensRestantes = new File(fotosPath).listFiles().length;
//
//            List<Resposta> respostasImagem = respostaDS.listarTodasImagens();
//            for (Resposta respostaImagem : respostasImagem) {
//                Bitmap foto = BitmapFactory.decodeFile(respostaImagem.getDados());
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                foto.compress(Bitmap.CompressFormat.JPEG, 85, baos);
//                byte[] b = baos.toByteArray();
//                foto.recycle();
//                String encoded = Base64.encodeToString(b, Base64.DEFAULT);
//
//                JsonImagem jsonImagem = new JsonImagem();
//                jsonImagem.setAutenticacao(Util.obterUsuarioLogado(getApplicationContext()));
//                jsonImagem.setDados(encoded);
//                jsonImagem.setExtensao("jpg");
//                jsonImagem.setIdImagem(respostaImagem.getId());
//
//                if (enviarJsonImagem((new Gson()).toJson(jsonImagem), baseURL + Constantes.METODO_GRAVA_ENVIA_IMAGEM)) {
//                    Log.d(Constantes.TAG, "SyncRespostasService - IMAGEM " + respostaImagem.getId() + " enviada com sucesso!");
//                    respostaDS.marcarImagemEnviada(respostaImagem.getId());
//                    BitmapUtils.deleteImage(respostaImagem.getDados());
//                } else {
//                    Log.d(Constantes.TAG, "SyncRespostasService - IMAGEM " + respostaImagem.getId() + " erro no envio.");
//                    falhas++;
//                }
//                sendStatus(imagensRestantes--, IMAGENS_RESTANTES_EXTRA);
//            }
//            respostaDS.close();
//        } finally {
//            sendStatus(falhas, FALHAS_EXTRA);
//            pararServico();
//        }
    }

//    private boolean enviarJsonImagem(String json, String url) {
//        try {
//            String resposta = ServiceRequest.makeJSONRequest(url, json);
//            JSONObject response = new JSONObject(resposta);
//
//            return response.getString("status").equalsIgnoreCase("OK");
//        } catch (ParseException e) {
//            Log.d(Constantes.TAG, "SyncRespostasService - ParseException " + e.getMessage());
//        } catch (ClientProtocolException e) {
//            Log.d(Constantes.TAG, "SyncRespostasService - ClientProtocolException " + e.getMessage());
//        } catch (IOException e) {
//            Log.d(Constantes.TAG, "SyncRespostasService - IOException " + e.getMessage());
//        } catch (IllegalStateException e) {
//            Log.d(Constantes.TAG, "SyncRespostasService - IllegalStateException " + e.getMessage());
//        } catch (JSONException e) {
//            Log.d(Constantes.TAG, "SyncRespostasService - JSONException " + e.getMessage());
//        }
//
//        return false;
//    }

//    private boolean enviarJsonQuestionario(String json, String url, Questionario questionarioLocal) {
//
//        try {
//            //System.out.println(json);
//            String resposta = ServiceRequest.makeJSONRequest(url, json);
//            RetornoSync retornoSync = (new Gson()).fromJson(resposta, RetornoSync.class);
//
//            if (!retornoSync.getStatus().equalsIgnoreCase("OK")) return false;
//
//            RespostaDataSource respostaDS = new RespostaDataSource(getApplicationContext());
//            for (Foto foto : retornoSync.getImagens()) {
//                respostaDS.inserirImagem(foto.getIdImagem(), getPathImg(foto.getIdOpcao(), questionarioLocal));
//            }
//
//            return true;
//        } catch (ParseException e) {
//            Log.d(Constantes.TAG, "SyncRespostasService - ParseException " + e.getMessage());
//        } catch (ClientProtocolException e) {
//            Log.d(Constantes.TAG, "SyncRespostasService - ClientProtocolException " + e.getMessage());
//        } catch (IOException e) {
//            Log.d(Constantes.TAG, "SyncRespostasService - IOException " + e.getMessage());
//        } catch (IllegalStateException e) {
//            Log.d(Constantes.TAG, "SyncRespostasService - IllegalStateException " + e.getMessage());
//        }
//
//        return false;
//    }

//    private String getPathImg(int idOpcao, Questionario questionario) {
//        if (idOpcao == 0) {
//            return questionario.getPathFotoComentario();
//        }
//
//        for (Categoria categoria : questionario.getCategorias()) {
//            for (Pergunta pergunta : categoria.getPerguntas()) {
//                for (OpcaoPergunta opcaoPergunta : pergunta.getOpcoes()) {
//                    if (opcaoPergunta.getId() == idOpcao) {
//                        return pergunta.getPathImgResposta();
//                    }
//                }
//            }
//        }
//        return null;
//    }

    private void sendStatus(int restantes, String type) {
        Intent intent = new Intent(INTENT_STATUS_SYNC);
        intent.putExtra(type, restantes);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @UiThread
    void pararServico() {
        stopSelf();
    }

}
