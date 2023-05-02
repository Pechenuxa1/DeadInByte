package ru.nsu.fit.dib.projectdib.newMultiplayer.threads;

import java.util.List;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.GameAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Receiver;

public class ClientReceiverThread extends Thread {

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      Receiver receiver = new Receiver(MCClient.getClientSocket());
      var inPacket = receiver.receive();
      switch (inPacket.getKey()) {
        case UPDATE -> {
          MCClient.getClientState().updateEntities((List<EntityState>) inPacket.getValue());
        }
        case SPAWN, WEAPON -> {
          GameAction gameAction = (GameAction) inPacket.getValue();
          gameAction.run();
        }
      }
    }
  }

}