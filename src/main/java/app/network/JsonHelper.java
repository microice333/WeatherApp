package app.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.util.CharsetUtil;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;

public class JsonHelper {
	public static JsonObject asJsonObject(String s) {
		return parse(s).getAsJsonObject();
	}

	public static JsonArray asJsonArray(String s) {
		return parse(s).getAsJsonArray();
	}

	private static JsonElement parse(String s) {
		return new JsonParser().parse(s);
	}
	
	public static HttpClientRequest<ByteBuf> withJsonHeader(HttpClientRequest<ByteBuf> request) {
		return request.withHeader(HttpHeaderNames.ACCEPT.toString(), HttpHeaderValues.APPLICATION_JSON.toString())
				.withHeader(HttpHeaderNames.ACCEPT_CHARSET.toString(), CharsetUtil.UTF_8.name());
	}
}
