package controller;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapDumper;
import org.pcap4j.packet.Packet;

public class CihazDinleyici implements PacketListener {

	private PcapDumper dumper;

	public CihazDinleyici(PcapDumper dumper) {
		this.dumper = dumper;
	}

	@Override
	public void gotPacket(Packet packet) {

		try {
			// System.err.println(packet);
			dumper.dump(packet);
		} catch (NotOpenException e) {
			e.printStackTrace();
		}
	}

	public void close() {

		if (dumper != null)
			dumper.close();
	}

}
