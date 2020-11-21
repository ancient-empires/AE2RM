package aeii;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ImageProcessor
{
	protected static final int FP_SHIFT = 16;
	protected static final int FP_MASK = (1 << FP_SHIFT) - 1;

	/**
	 * Функция масштабирования изображений.
	 *
	 * Если один из конечных размеров меньше 0,
	 * он вычисляется на основе другого с сохранением пропорций.
	 *
	 * Если оба конечных размера меньше 0,
	 * или они равны размеру исходной картинки,
	 * возвращается исходная картинка.
	 *
	 * При масштабировании может использоваться бикубическая интерполяция
	 * для получения более качественных изображений (особенно при увеличении).
	 *
	 * Если для бикубической интерполяции не хватит памяти,
	 * будет использоваться билинейная интерполяция.
	 *
	 * Если для масштабирования всей картинки не хватит памяти,
	 * она будет масштабироваться по частям,
	 * при этом альфа-канал не сохраняется.
	 *
	 * @param source исходная картинка
	 * @param destw конечная ширина
	 * @param desth конечная высота
	 * @param alpha true, если нужно сохранить альфа-канал
	 * @return масштабированная картинка
	 */
	public static Image scaleImage(Image source, int destw, int desth, boolean alpha)
	{
		if(source == null)
		{
			return null;
		}

		try
		{
			int srcw = source.getWidth();
			int srch = source.getHeight();

			if(srcw == destw && srch == desth)
			{
				return source;
			}

			if(destw < 0)
			{
				if(desth < 0)
				{
					return source;
				}
				else
				{
					destw = srcw * desth / srch;
				}
			}
			else if(desth < 0)
			{
				desth = srch * destw / srcw;
			}

			int[] src = null;
			int[] dst = null;
			int fragcount = 1;
			int srcfw, srcfh;
			int dstfw, dstfh;

			do
			{
				srcfw = srcw / fragcount;
				srcfh = srch / fragcount;
				dstfw = destw / fragcount;
				dstfh = desth / fragcount;

				try
				{
					src = new int[srcfw * srcfh];
					dst = new int[dstfw * dstfh];
				}
				catch(OutOfMemoryError oome)
				{
					src = null;
					dst = null;
					fragcount++;
				}
			}
			while(src == null || dst == null);

			if(fragcount == 1)
			{
				source.getRGB(src, 0, srcw, 0, 0, srcw, srch);

				scaleRGB(src, dst, srcw, srch, destw, desth, alpha);

				return Image.createRGBImage(dst, destw, desth, alpha);
			}
			else
			{
				Image scaled = Image.createImage(destw, desth);
				Graphics g = scaled.getGraphics();

				int fx, fy;

				for(fx = 0; fx < fragcount; fx++)
				{
					for(fy = 0; fy < fragcount; fy++)
					{
						source.getRGB(src, 0, srcfw, fx * srcfw, fy * srcfh, srcfw, srcfh);

						scaleRGB(src, dst, srcfw, srcfh, dstfw, dstfh, false);

						g.drawRGB(dst, 0, dstfw, fx * dstfw, fy * dstfh, dstfw, dstfh, false);
					}
				}

				return scaled;
			}
		}
		catch(Throwable t)
		{
			return source;
		}
	}

	/**
	 * Масштабирование массива RGB / ARGB точек.
	 * Используется метод усреднения / ближайшей точки.
	 *
	 * @param srcPixels исходный массив
	 * @param destPixels конечный массив
	 * @param srcw исходная ширина
	 * @param srch исходная высота
	 * @param destw конечная ширина
	 * @param desth конечная высота
	 * @param alpha массив ARGB
	 */
	public static void scaleRGB(int[] srcPixels, int[] destPixels, int srcw, int srch, int destw, int desth, boolean alpha)
	{
		int wscan, hscan, scancount;
		int x, y, sx, sy, cx, cy;
		int a, r, g, b;
		int idx;
		int rx, ry;

		rx = (srcw << FP_SHIFT) / destw;
		ry = (srch << FP_SHIFT) / desth;

		wscan = rx >>> FP_SHIFT;
		hscan = ry >>> FP_SHIFT;

		if(wscan == 0)
		{
			wscan = 1;
		}

		if(hscan == 0)
		{
			hscan = 1;
		}

		scancount = wscan * hscan;

		try
		{
			if(alpha)
			{
				for(x = 0; x < destw; x++)
				{
					for(y = 0; y < desth; y++)
					{
						sx = (x * rx) >>> FP_SHIFT ;
						sy = (y * ry) >>> FP_SHIFT;

						a = r = g = b = 0;

						for(cx = 0; cx < wscan; cx++)
						{
							for(cy = 0; cy < hscan; cy++)
							{
								idx = sx + cx + (sy + cy) * srcw;

								a += (srcPixels[idx] >> 24) & 0xFF;
								r += (srcPixels[idx] >> 16) & 0xFF;
								g += (srcPixels[idx] >> 8) & 0xFF;
								b += (srcPixels[idx]) & 0xFF;
							}
						}

						a /= scancount;
						r /= scancount;
						g /= scancount;
						b /= scancount;

						destPixels[x + y * destw] = (a << 24) | (r << 16) | (g << 8) | b;
					}
				}
			}
			else
			{
				for(x = 0; x < destw; x++)
				{
					for(y = 0; y < desth; y++)
					{
						sx = (x * rx) >>> FP_SHIFT ;
						sy = (y * ry) >>> FP_SHIFT;

						r = g = b = 0;

						for(cx = 0; cx < wscan; cx++)
						{
							for(cy = 0; cy < hscan; cy++)
							{
								idx = sx + cx + (sy + cy) * srcw;

								r += (srcPixels[idx] >> 16) & 0xFF;
								g += (srcPixels[idx] >> 8) & 0xFF;
								b += (srcPixels[idx]) & 0xFF;
							}
						}

						r /= scancount;
						g /= scancount;
						b /= scancount;

						destPixels[x + y * destw] = (r << 16) | (g << 8) | b;
					}
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
		}
	}
}