package net.untoldwind.moredread.model.io.impl.collada;

import java.io.IOException;
import java.io.OutputStream;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;

import net.untoldwind.moredread.model.io.spi.IModelWriter;
import net.untoldwind.moredread.model.scene.Scene;

import org.collada._2005._11.colladaschema.Asset;
import org.collada._2005._11.colladaschema.COLLADA;
import org.collada._2005._11.colladaschema.InstanceWithExtra;
import org.collada._2005._11.colladaschema.UpAxisType;

public class ColladaWriter implements IModelWriter {
	@Override
	public void writeScene(final Scene scene, final OutputStream out)
			throws IOException {
		final DatatypeFactory datatypeFactory = ColladaContext.getInstance()
				.createDatatypeFactory();

		final COLLADA collada = new COLLADA();

		collada.setVersion(ColladaContext.VERSION);

		final Asset asset = new Asset();
		collada.setAsset(asset);
		asset.setTitle(scene.getName());
		asset.setCreated(datatypeFactory
				.newXMLGregorianCalendar(new GregorianCalendar()));
		asset.setUpAxis(UpAxisType.Y_UP);
		final Asset.Unit unit = new Asset.Unit();
		unit.setMeter(1.0);
		unit.setName("Meter");
		asset.setUnit(unit);

		final COLLADA.Scene colladaScene = new COLLADA.Scene();
		collada.setScene(colladaScene);
		final InstanceWithExtra instanceWithExtra = new InstanceWithExtra();
		instanceWithExtra.setUrl("#VisualSceneNode");
		colladaScene.setInstanceVisualScene(instanceWithExtra);
	}
}
