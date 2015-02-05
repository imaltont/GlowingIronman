/**
 * Created with IntelliJ IDEA.
 * User: Sondre Hegdal
 * Date: 12.01.15
 * Time: 20:07
 * To change this template use File | Settings | File Templates.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class GUI extends JPanel
{
    //Panels and groups
    protected JPanel panel;
    protected JPanel optionPanel;
    protected JPanel generatePanel;
    protected JScrollPane generateScroll;
    protected  JScrollPane mapScroll;

    //Textfields and areas
    public JTextArea mapsToChoose;
    public JTextArea chosenMaps;
    public JLabel fromLabel;
    public JLabel toLabel;
    public JTextField fromRound;
    public JTextField toRound;

    //Buttons
    public JButton updateMaps;
    public JButton setRounds;

    //Combo boxes
    public JComboBox<String> roundFormat;

    public Maps maps;

    public GUI () throws IOException {
        maps = new Maps();
        this.setLayout(new GridLayout(0, 2));

        GridBagConstraints gBC = new GridBagConstraints();

        panel = new JPanel();
        panel.setSize(25, 10);
        panel.setLayout(new GridBagLayout());
        this.add(panel);



        mapsToChoose = new JTextArea();
        mapsToChoose.setText(maps.getMaps());
        gBC.gridy = 0;
        mapsToChoose.setRows(7);
        mapsToChoose.setColumns(15);

        mapScroll = new JScrollPane(mapsToChoose);
        panel.add(mapScroll, gBC);

        updateMaps = new JButton("Oppdater maps");
        updateMaps.addActionListener(new UpdateMaps());
        updateMaps.setName("UpdateMaps");
        gBC.gridy = 1;
        panel.add(updateMaps, gBC);

        optionPanel = new JPanel();
        optionPanel.setSize(25, 10);
        optionPanel.setLayout(new GridBagLayout());
        this.add(optionPanel);

        fromLabel = new JLabel("Fra runde: ");
        gBC.gridy = 0;
        gBC.gridx = 0;
        optionPanel.add(fromLabel, gBC);

        fromRound = new JTextField();
        fromRound.setColumns(5);
        gBC.gridy = 0;
        gBC.gridx = 1;
        optionPanel.add(fromRound, gBC);

        toLabel = new JLabel("Til runde: ");
        gBC.gridy = 1;
        gBC.gridx = 0;
        optionPanel.add(toLabel, gBC);

        toRound = new JTextField();
        toRound.setColumns(5);
        gBC.gridy = 1;
        gBC.gridx = 1;
        optionPanel.add(toRound, gBC);

        roundFormat = new JComboBox<String>(new String[]{"Proleague", "All-kill", "Hybrid"});
        gBC.gridy = 2;
        gBC.gridx = 1;
        optionPanel.add(roundFormat, gBC);

        setRounds = new JButton("Generer Maps");
        setRounds.addActionListener(new UpdateMaps());
        setRounds.setName("SetMaps");
        setRounds.setSize(5, 2);
        setRounds.addActionListener(new SetRounds());
        gBC.gridy = 3;
        gBC.gridx = 1;
        optionPanel.add(setRounds, gBC);

        generatePanel = new JPanel();
        generatePanel.setLayout(new GridLayout());
        this.add(generatePanel);


        chosenMaps = new JTextArea();
        chosenMaps.setLineWrap(true);
        chosenMaps.setEditable(false);

        generateScroll = new JScrollPane(chosenMaps);
        generatePanel.add(generateScroll);


    }

    class UpdateMaps implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.

            try
            {
                java.util.List<String> currentMaps = Arrays.asList(mapsToChoose.getText().split("\\n"));
                maps.updateMaps(currentMaps);
            } catch (IOException e1)
            {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    class SetRounds implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
            int start = Integer.parseInt(fromRound.getText());
            int end = Integer.parseInt(toRound.getText());
            int numberOfRounds = end-start;
            chosenMaps.setText("");
            if (roundFormat.getSelectedItem().equals("Proleague"))
            {
                for (int i = start; i <= end; i++)
                {
                    try
                    {
                        //5 for antall maps i proleague format
                        chosenMaps.setText(chosenMaps.getText() + "Runde: " + i + "\n" + getMaps(5) + "\n\n");
                    } catch (IOException e1)
                    {
                        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
            else if (roundFormat.getSelectedItem().equals("All-kill"))
            {
                for (int i = start; i <= end; i++)
                {
                    try
                    {
                        //5 for antall maps i proleague format
                        chosenMaps.setText(chosenMaps.getText() + "Runde: " + i + "\n" + getMaps(1) + "\n\n");
                    } catch (IOException e1)
                    {
                        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
            else if (roundFormat.getSelectedItem().equals("Hybrid"))
            {
                for (int i = start; i <= end; i++)
                {
                    try
                    {
                        //5 for antall maps i proleague format
                        chosenMaps.setText(chosenMaps.getText() + "Runde: " + i + "\n" + "Proleague: " + "\n" + getMaps(4) + "All-kill: " + "\n" + getMaps(1) + "\n\n");
                    } catch (IOException e1)
                    {
                        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
        }
        public String getMaps(int rounds) throws IOException {
            java.util.List<String> workingList = new ArrayList<String>(maps.getCurrentMaps());
            java.util.List<String> roundMaps = new ArrayList<String>();
            Random rand = new Random();
            for (int i = 0; i < rounds; i++)
            {
                int randomInt = rand.nextInt(workingList.size());
                roundMaps.add(workingList.get(randomInt));
                workingList.remove(randomInt);
            }
            String roundMapList = "";
            for (String line : roundMaps)
            {
                roundMapList += line + "\n";
            }
            return roundMapList;

        }
    }

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Map velger");
        frame.getContentPane().add(new GUI());
        frame.pack();
        frame.setVisible(true);
    }
}

