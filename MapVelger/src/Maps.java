import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sondre Hegdal
 * Date: 12.01.15
 * Time: 20:42
 * To change this template use File | Settings | File Templates.
 */
public class Maps
{
    private List <String> currentMaps;

    public String getMaps() throws IOException {
        String maps = "";
        Path mapPath = Paths.get("maps\\chooseMaps.txt");
        Charset charset = Charset.forName("ISO-8859-1");

        try
        {
            List<String> rawMaps = Files.readAllLines(mapPath, charset);
            currentMaps = rawMaps;

            for (int i = 0; i < currentMaps.size(); i++)
            {
                if (i != currentMaps.size()-1)
                {
                    maps += currentMaps.get(i) + "\n";
                }
                else
                {
                    maps += currentMaps.get(i);
                }
            }
        } catch (IOException e)
        {
            System.out.print(e);
        }
        return maps;
    }
    public void updateMaps (List <String> maps) throws FileNotFoundException, UnsupportedEncodingException {
        //legge inn action listener ol imorgen for den knappen som skal fikse dette, + gjøre så denne alltid skriver over det som var i den gamle filen fra før, og må vel kanskje legge inn en variabel som kan holde styr på alle maps som står der for øyeblikket, slik at den vet om de har blitt oppdatert, og ikke trenger å lese av filen for hevr gang den skal inn med en ny runde.
        PrintWriter writer = new PrintWriter("maps\\chooseMaps.txt", "ISO-8859-1");
        for (String line : maps)
        {
            writer.println(line);
        }
        writer.close();
        Path mapPath = Paths.get("maps\\chooseMaps.txt");
        Charset charset = Charset.forName("ISO-8859-1");

        try
        {
            currentMaps = Files.readAllLines(mapPath, charset);

        } catch (IOException e)
        {
            System.out.print(e);
        }

    }

    private void clearMaps ()
    {
        currentMaps.clear();
    }
    public List <String> getCurrentMaps()
    {
        return currentMaps;
    }
}
