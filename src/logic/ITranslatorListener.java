package logic;

public interface ITranslatorListener {	
	void onTranslationLoaded(String translation);
	void onLoadFailed();
	void onFailedConnection();
}
