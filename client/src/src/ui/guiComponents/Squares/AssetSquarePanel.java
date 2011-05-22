package src.ui.guiComponents.Squares;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import listeners.innerChangeEventListener.InnerChangeEventListner;
import listeners.innerChangeEventListener.InnerChangeEvet;
import src.assets.Asset;
import src.assets.City;
import src.assets.UtilOrTranspoAsset;
import src.assets.UtilOrTranspoAssetGroup;
import src.client.GameManager;




import ui.utils.TransparentTable;

/**
 * public class AssetSquarePanel extends SquarePanel
 * an SquarePanel representing an asset
 * @author Omer Shenhar and Shachar Butnaro
 *
 */
public class AssetSquarePanel extends SquarePanel {
	

	private DefaultTableModel AssetInformationModel;
	TransparentTable AssetInformation;
	JLabel SaleOrRentPrice=new JLabel();
	JLabel owner=new JLabel();
	JPanel DataArea= new JPanel(new GridLayout(0,1));
        Asset representedAsset;
	
	/**
	 * public AssetSquarePanel(Asset representedAsset)
	 * a constructor for an AssetSquarePanel makes an AssetSquarePanel with an hover mode
	 * @param representedAsset - a valid non null Asset square
	 */
	public AssetSquarePanel(Asset representedAsset)
	{
		this(representedAsset,true);
		owner.setFont(GameManager.DefaultFont);
		SaleOrRentPrice.setFont(GameManager.DefaultFont);
	}
	
	/**
	 * public AssetSquarePanel(Asset representedAsset,boolean enableHoverMode)
	 * a constructor for an AssetSquarePanel 
	 * @param representedAsset -a valid non null Asset square
	 * @param enableHoverMode - if set true hover mode is enabled. otherwise it is disabled
	 */
	public AssetSquarePanel(Asset representedAsset,boolean enableHoverMode) {
		super(representedAsset);
		this.representedAsset=representedAsset;
		//to disallow editing
		
		AssetInformation=new TransparentTable();
		
		groupLabel.setEnabled(true);
		groupLabel.setText(representedAsset.getGroup().getName()+":");
		groupLabel.setFont(GameManager.DefaultFont);
		
		titleLabel.setText(titleLabel.getText());
		titleLabel.setFont(GameManager.DefaultFont);
		UpdateTable();
		this.add(DataArea,BorderLayout.CENTER);
		if(enableHoverMode)
		{
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					makeHover();
				}
			});
			SaleOrRentPrice.setText("Cost:"+representedAsset.getCost());
			owner.setText("");
			DataArea.add(SaleOrRentPrice ,BorderLayout.CENTER);
			DataArea.add(owner ,BorderLayout.CENTER);
			this.setToolTipText("click the square for more details");
			
		}
		else
		{
			DataArea.add(AssetInformation,BorderLayout.CENTER);
		}
		representedAsset.addInnerChangeEventListner(new InnerChangeEventListner() {
			
			@Override
			public void eventHappened(InnerChangeEvet innerChangeEvet) {
				UpdateTable();
				
			}

			
		});
		
		this.validate();
		this.repaint();
		
	}
	/**
	 * private void makeHover()
	 * opens an hover window with the info table 
	 */
	private void makeHover() {
		
		hoverDialog hoverInfo= new hoverDialog(this);
		hoverInfo.setVisible(true);
		
		
	}

	/**
	 * public void UpdateTable()
	 * updates the table to hold the current represent square information
	 */
	public void UpdateTable(){
		if(representedAsset.getOwner()!=GameManager.assetKeeper)
		{
			owner.setText("owner "+ representedAsset.getOwner().getName());
			SaleOrRentPrice.setText("rent:"+representedAsset.getRentPrice());
		}
		else
		{
			owner.setText("");
			SaleOrRentPrice.setText("Cost:"+representedAsset.getCost());
		}
		AssetInformation.resetModel();
		AssetInformationModel=(DefaultTableModel) AssetInformation.getModel();
		AssetInformation.setModel(AssetInformationModel);
			AssetInformation.setCellSelectionEnabled(false);
			AssetInformation.setColumnSelectionAllowed(false);
			AssetInformationModel.addColumn("what");
			AssetInformationModel.addColumn("value");
			
			boolean hasOwner=representedAsset.getOwner()!=GameManager.assetKeeper;
			
			AssetInformationModel.addRow(new String[]{"Current Owner:",""+
					((hasOwner)? representedAsset.getOwner().getName():"Tresury" )});
			AssetInformationModel.addRow(new String[]{"Market Price:",""+representedAsset.getCost()});
			AssetInformationModel.addRow(new String[]{"Current Rent Price:",""+
					((hasOwner)? representedAsset.getRentPrice():0)});
			if (representedAsset instanceof City) {
				int[] prices=((City) representedAsset).getPrices();
				AssetInformationModel.addRow(new String[]{"Base rent Price :",""+representedAsset.getRentPrice()});
				for (int i = 1; i < prices.length; i++) {
					AssetInformationModel.addRow(new String[]{"Rent Price with "+i+" houses:",""+prices[i]});

				}
				
			}
			
			else if(representedAsset instanceof UtilOrTranspoAsset) 
			{
				UtilOrTranspoAsset temp=(UtilOrTranspoAsset)representedAsset;
				UtilOrTranspoAssetGroup tempGroup=((UtilOrTranspoAssetGroup)temp.getGroup());
				AssetInformationModel.addRow(new String[]{"Rent for single Asset:",""+temp.getBasicRent()});
				AssetInformationModel.addRow(new String[]{"Rent for entire group:",""+tempGroup.getFullRental()});
				
			}
			AssetInformation.setFont(new Font("Tahoma", Font.PLAIN, 8));
	}
	
	
	
	
	private static final long serialVersionUID = 1L;

}
